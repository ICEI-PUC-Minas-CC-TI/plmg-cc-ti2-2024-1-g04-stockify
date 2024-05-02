//Função cadastra funcionário

function cadastraFornecedor(event) {
    event.preventDefault();
    const formData = {
      fornecedor: document.getElementById('fornecedor').value,
      categoria: document.getElementById('categoria').value,
      celular: document.getElementById('celular').value,
      endereco: document.getElementById('endereco').value
    };
  
    fetch('https://jsonserver.samaranegabriel.repl.co/fornecedores', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(formData),
    })
      .then(response => response.json())
      alert("FORNECEDOR CADASTRADO COM SUCESSO")
      .then(data => {
        console.log('Sucesso:', data);
        window.location.href = '../index.html'; // para redirecionar para a página inicial
      })
      .catch((error) => {
        console.error('Erro:', error);
      });
  }
  
  document.addEventListener('DOMContentLoaded', function() {
    document.getElementById('cadastroForm').addEventListener('submit', cadastraFornecedor);
  });