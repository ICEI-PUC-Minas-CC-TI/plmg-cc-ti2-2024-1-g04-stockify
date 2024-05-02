//Função cadastra funcionário

function cadastraFuncionario(event) {
  event.preventDefault();
  const formData = {
    nome: document.getElementById('nome').value,
    email: document.getElementById('email').value,
    senha: document.getElementById('senha').value,
    idade: parseInt(document.getElementById('idade').value, 10),
    cpf: document.getElementById('cpf').value,
    salario: parseFloat(document.getElementById('salario').value)
  };

  fetch('/login', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(formData),
  })
    .then(response => response.json())
    alert("FUNCIONARIO CADASTRADO COM SUCESSO")
    .then(data => {
      console.log('Sucesso:', data);
      alert('Funcionário cadastrado com sucesso!');
    })
    .catch((error) => {
      console.error('Erro:', error);
    });
}

document.addEventListener('DOMContentLoaded', function() {
  document.getElementById('cadastroForm').addEventListener('submit', cadastraFuncionario);
});