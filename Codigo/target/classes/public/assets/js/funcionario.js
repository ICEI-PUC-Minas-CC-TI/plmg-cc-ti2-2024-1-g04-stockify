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
  const url = searchValue ? `https://seu-backend.com/usuarios?nome_like=${searchValue}` : 'https://seu-backend.com/usuarios';

  fetch(url)
    .then(response => response.json())
    .then(usuarios => {
      const container = document.getElementById('employeeCardContainer');
      container.innerHTML = ''; // Limpa os resultados anteriores

      if (usuarios.length === 0) {
        // Se nenhum usuário for encontrado, exiba a mensagem.
        container.innerHTML = '<p>USUÁRIO NÃO ENCONTRADO</p>';
      } else {
        usuarios.forEach(usuario => {
          const card = document.createElement('div');
          card.classList.add('employeeCard');
          card.innerHTML = `
            <header>
              <h2>${usuario.nome}</h2>
            </header>
            <p>ID: ${usuario.id}</p>
            <p>Email: ${usuario.email}</p>
            <p>CPF: ${usuario.cpf}</p>
            <p>Salário: ${usuario.salario}</p>
            <!-- Adicione mais campos conforme necessário -->
            <button onclick="deleteUsuario(${usuario.id})">Deletar</button>
          `;
          container.appendChild(card);
        });
      }
    })
    .catch(error => console.error('Erro ao buscar usuários:', error));
}

function deleteUsuario(id) {
  if (isAdmin()) {
    fetch(`https://seu-backend.com/usuarios/${id}`, {
      method: 'DELETE'
    })
      .then(response => {
        if (response.ok) {
          alert('Usuário deletado com sucesso!');
          window.location.reload(); // Atualiza a página para mostrar os novos usuários
        } else {
          alert('Erro ao deletar usuário!');
        }
      })
  }
  else {
    alert("SOMENTE ADMINISTRADORES PODEM DELETAR USUÁRIOS");
  }
}
