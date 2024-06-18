document.addEventListener('DOMContentLoaded', () => {
  handleSearch();

  // Adicionar listener ao botão de pesquisa
  const searchButton = document.getElementById('searchButton');
  searchButton.addEventListener('click', handleSearch);

  // Adicionar listener ao input de pesquisa para acionar a busca ao digitar
  const searchInput = document.getElementById('searchInput');
  searchInput.addEventListener('input', handleSearch);
});

async function handleSearch() {
  try {
      const searchInput = document.getElementById('searchInput').value.toLowerCase();
      const funcionarios = await fetchFuncionarios();
      const filteredFuncionarios = funcionarios.filter(funcionario => 
          funcionario.username.toLowerCase().includes(searchInput)
      );
      displayResults(filteredFuncionarios);
  } catch (error) {
      console.error('Erro:', error);
      alert('Ocorreu um erro ao retornar os funcionários. Por favor, tente novamente.');
  }
}

async function fetchFuncionarios() {
  try {
      const response = await fetch('/funcionarios/getAll', {
          method: 'GET',
          headers: {
              'Content-Type': 'application/json',
          }
      });

      if (response.status === 404) {
          return [];
      }

      if (!response.ok) {
          throw new Error('Erro ao obter dados do servidor');
      }

      return await response.json();
  } catch (error) {
      console.error('Erro:', error);
      alert('Ocorreu um erro ao retornar os funcionários. Por favor, tente novamente.');
      throw error;
  }
}

function displayResults(funcionarios) {
  const employeeCardContainer = document.getElementById('employeeCardContainer');
  if (!employeeCardContainer) {
      console.error("Contêiner de cards de funcionários não encontrado no DOM.");
      return;
  }

  employeeCardContainer.innerHTML = '';

  if (funcionarios && funcionarios.length > 0) {
      funcionarios.forEach(funcionario => {
          const employeeCard = createEmployeeCard(funcionario);
          employeeCardContainer.appendChild(employeeCard);
      });
  } else {
      employeeCardContainer.innerHTML = '<p>Nenhum funcionário cadastrado.</p>';
  }
}

function createEmployeeCard(funcionario) {
  const card = document.createElement('div');
  card.classList.add('employeeCard');

  const cardContent = `
      <h3>${funcionario.username}</h3>
      <p>Email: ${funcionario.email}</p>
      <p>CPF: ${funcionario.cpf}</p>
      <p>Salario: R$${funcionario.salario}</p>
      <p>Idade: ${funcionario.idade}</p>
  `;

  card.innerHTML = cardContent;

  card.addEventListener('click', () => {
      redirectToEmployeePage(funcionario.id);
  });

  return card;
}

function redirectToEmployeePage(employeeId) {
  window.location.href = `atualizaFuncionario.html?id=${employeeId}`;
}
