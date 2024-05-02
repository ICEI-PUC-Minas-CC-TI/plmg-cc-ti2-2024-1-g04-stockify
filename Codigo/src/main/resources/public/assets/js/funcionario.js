function isAdmin() {
  const usuarioJson = localStorage.getItem("Usuario");
  if (usuarioJson) {
    const usuario = JSON.parse(usuarioJson);
    return usuario.admin === true;
  }
  return false;
}

function searchEmployee() {
  console.log("clicked");
  const searchValue = document.getElementById('searchInput').value;
  const url = searchValue ? `https://jsonserver.samaranegabriel.repl.co/funcionarios?nome_like=${searchValue}` : 'https://jsonserver.samaranegabriel.repl.co/funcionarios';

  fetch(url)
    .then(response => response.json())
    .then(employees => {
      const container = document.getElementById('employeeCardContainer');
      container.innerHTML = ''; // Limpa os resultados anteriores

      if (employees.length === 0) {
        // Se nenhum funcionário for encontrado, exiba a mensagem.
        container.innerHTML = '<p>FUNCIONÁRIO NÃO ENCONTRADO</p>';
      } else {
        employees.forEach(employee => {
          const card = document.createElement('div');
          card.classList.add('employeeCard');
          card.innerHTML = `
            <h2>${employee.nome}</h2>
            <p>ID: ${employee.id}</p>
            <p>Email: ${employee.email}</p>
            <p>Senha: ${employee.senha}</p>
            <p>CPF: ${employee.cpf}</p>
            <p>Salario: ${employee.salario}</p>
            <button onclick="deleteFuncionario(${employee.id})">Deletar</button>
          `;
          container.appendChild(card);
        });
      }
    })
    .catch(error => console.error('Erro ao buscar funcionários:', error));
}


function showAll() {
  fetch('https://jsonserver.samaranegabriel.repl.co/funcionarios')
    .then(response => response.json())
    .then(employees => {
      const container = document.getElementById('employeeCardContainer');
      container.innerHTML = ''; // Limpa os resultados anteriores
      employees.forEach(employee => {
        // Usar um elemento 'article' para cada card
        const card = document.createElement('article');
        card.classList.add('employeeCard');
        card.innerHTML = `
                  <header>
                    <h2>${employee.nome}</h2>
                  </header>
                  <p>ID: ${employee.id}</p>
                  <p>Email: ${employee.email}</p>
                  <p>Senha: ${employee.senha}</p>
                  <p>CPF: ${employee.cpf}</p>
                  <p>Salario: ${employee.salario}</p>
                  <!-- Adicione mais campos conforme necessário -->
                  <button onclick="deleteFuncionario(${employee.id})">Deletar</button>
              `;
        container.appendChild(card);
      });
    })
    .catch(error => console.error('Erro ao buscar funcionários:', error));
}

function deleteFuncionario(id) {
  if (isAdmin()) {
    fetch(`https://jsonserver.samaranegabriel.repl.co/funcionarios/${id}`, {
      method: 'DELETE'
    })
      .then(response => {
        if (response.ok) {
          alert('Funcionário deletado com sucesso!');
          window.location.reload(); // Atualiza a página para mostrar os novos funcionários
        } else {
          alert('Erro ao deletar funcionário!');
        }
      })
  }
  else {
    alert("SOMENTE ADMINISTRADORES PODEM DELETAR FUNCIONÁRIOS");
  }

   
}

function updateFuncionario(id) {
  if (isAdmin()) {
    fetch(`https://jsonserver.samaranegabriel.repl.co/funcionarios/${id}`, {
      method: 'PUT'
    })
      .then(response => {
        if (response.ok) {
          alert('Funcionário deletado com sucesso!');
          window.location.reload(); // Atualiza a página para mostrar os novos funcionários
        } else {
          alert('Erro ao deletar funcionário!');
        }
      })
  }
  else {
    alert("SOMENTE ADMINISTRADORES PODEM DELETAR FUNCIONÁRIOS");
  }

}
