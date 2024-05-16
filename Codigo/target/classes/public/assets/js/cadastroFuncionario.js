function cadastraFuncionario(event) {
  event.preventDefault();
  
  const usernameElement = document.getElementById('username');
  const emailElement = document.getElementById('email');
  const passwordElement = document.getElementById('password');
  
  if (usernameElement && emailElement && passwordElement) {
    const formData = {
      username: usernameElement.value,
      email: emailElement.value,
      senha: passwordElement.value,
    };
    
    console.log(formData);

    fetch('/login/insere', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(formData),
    })
      .then(response => response.json())
      .then(data => {
        console.log('Sucesso:', data);
        alert('Funcionário cadastrado com sucesso!');
        // Redireciona para a página funcionario.html após o cadastro
        window.location.href = 'funcionarios.html';
      })
      .catch((error) => {
        console.error('Erro:', error);
        console.log('Ocorreu um erro ao cadastrar o funcionário. Por favor, tente novamente.');
      });
  } else {
    console.error('Elementos do formulário não encontrados.');
    alert('Ocorreu um erro ao acessar os elementos do formulário. Por favor, tente novamente.');
  }
}

document.addEventListener('DOMContentLoaded', function() {
  document.getElementById('cadastroForm').addEventListener('submit', cadastraFuncionario);
});
