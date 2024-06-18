function cadastraFuncionario(event) {
  event.preventDefault();
  
  const usernameElement = document.getElementById('username');
  const emailElement = document.getElementById('email');
  const passwordElement = document.getElementById('password');
  const idadeElement = document.getElementById('idade');
  const cpfElement = document.getElementById('cpf');
  const salarioElement = document.getElementById('salario');
  
  if (usernameElement && emailElement && passwordElement && idadeElement && cpfElement && salarioElement) {
    // Validar idade não maior que 90 anos
    const idade = parseInt(idadeElement.value);
    if (idade > 90) {
      alert('A idade não pode ser maior que 90 anos.');
      return;
    }

    // Validar CPF deve ter exatamente 11 dígitos
    const cpf = cpfElement.value.replace(/[^\d]/g, ''); // Remove caracteres não numéricos do CPF
    if (cpf.length !== 11) {
      alert('O CPF deve ter exatamente 11 dígitos.');
      return;
    }

    const formData = {
      username: usernameElement.value,
      email: emailElement.value,
      senha: passwordElement.value,
      idade: idadeElement.value,
      cpf: cpf,
      salario: salarioElement.value,
    };
    

    fetch('/login/insere', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(formData),
    })
      .then(response => response.json())
      .then(data => {
        alert('Funcionário cadastrado com sucesso!');
        // Redireciona para a página funcionario.html após o cadastro
        window.location.href = 'funcionarios.html';
      })
      .catch((error) => {
        console.error('Erro:', error);
        alert('Ocorreu um erro ao cadastrar o funcionário. Por favor, tente novamente.');
      });
  } else {
    console.error('Elementos do formulário não encontrados.');
    alert('Ocorreu um erro ao acessar os elementos do formulário. Por favor, tente novamente.');
  }
}

document.addEventListener('DOMContentLoaded', function() {
  document.getElementById('cadastroForm').addEventListener('submit', cadastraFuncionario);
});
