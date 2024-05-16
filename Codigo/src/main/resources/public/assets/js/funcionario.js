document.addEventListener('DOMContentLoaded', function() {
  getAllUsuarios();

  const searchInput = document.getElementById('searchInput');
  searchInput.addEventListener('input', function() {
      const term = this.value.trim().toLowerCase();
      filterUsuarios(term);
  });
});

function filterUsuarios(term) {
  fetch('/funcionarios/getAll')
      .then(response => response.json())
      .then(data => {
          const filteredUsuarios = data.filter(usuario => {
              return usuario.email.toLowerCase().includes(term) ||
                     usuario.username.toLowerCase().includes(term);
          });
          createEmployeeCards(filteredUsuarios);
      })
      .catch(error => console.error('Erro ao obter usuários:', error));
}

function getAllUsuarios() {
  fetch('/funcionarios/getAll')
      .then(response => response.json())
      .then(data => {
          // Chame uma função para criar os cards com base nos dados recebidos
          createEmployeeCards(data);
      })
      .catch(error => console.error('Erro ao obter usuários:', error));
}

function createEmployeeCards(usuarios) {
  const container = document.getElementById('employeeCardContainer');
  container.innerHTML = ''; // Limpar qualquer conteúdo anterior

  usuarios.forEach(usuario => {
      const card = document.createElement('div');
      card.classList.add('employeeCard');
      card.style.cursor = 'pointer';
      card.addEventListener('click', function() {
          // Adicione o ID do funcionário como um parâmetro na URL
          window.location.href = `../pages/atualizaFuncionario.html?id=${usuario.id}`;
      });

      const header = document.createElement('header');
      header.textContent = `ID: ${usuario.id}`;

      const email = document.createElement('p');
      email.textContent = `email: ${usuario.email}`;

      const username = document.createElement('p');
      username.textContent = `username: ${usuario.username}`;

      card.appendChild(header);
      card.appendChild(email);
      card.appendChild(username);

      container.appendChild(card);
  });
}

document.addEventListener('DOMContentLoaded', function() {
  getAllUsuarios();
});

// Na página de atualização (atualizaFuncionario.html)
document.addEventListener('DOMContentLoaded', function() {
  const urlParams = new URLSearchParams(window.location.search);
  const userId = urlParams.get('id');

  console.log("ID do usuário recebido:", userId); // Verificar se o ID do usuário está sendo recebido corretamente

  // Exibir o ID do funcionário na página
  const employeeIdSpan = document.getElementById('employeeId');
  console.log("Elemento employeeIdSpan:", employeeIdSpan); // Verificar se o elemento com o ID 'employeeId' foi encontrado

  if (employeeIdSpan) {
      employeeIdSpan.textContent = userId; // Definir o texto apenas se o elemento existir
  } else {
      console.error("Elemento employeeIdSpan não encontrado."); // Registrar um erro se o elemento não for encontrado
  }
});


