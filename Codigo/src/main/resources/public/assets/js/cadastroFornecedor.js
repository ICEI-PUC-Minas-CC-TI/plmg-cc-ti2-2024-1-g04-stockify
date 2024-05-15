//Função cadastra funcionário

function cadastraFornecedor(event) {
  event.preventDefault();
  const formData = {
    nome: document.getElementById('fornecedor').value,
    categoria: document.getElementById('categoria').value,
    celular: document.getElementById('celular').value,
    endereco: document.getElementById('endereco').value
  };

  fetch('/fornecedor/insere', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(formData),
  })
    .then(response => response.json())
    .then(data => {
      alert("FORNECEDOR CADASTRADO COM SUCESSO"); // Movido o alert para dentro do bloco .then()
      console.log('Sucesso:', data);
    })
    .catch((error) => {
      console.error('Erro:', error);
    });
}

document.addEventListener('DOMContentLoaded', function() {
  document.getElementById('cadastroForm').addEventListener('submit', cadastraFornecedor);
});