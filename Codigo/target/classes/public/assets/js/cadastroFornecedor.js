// Função cadastra funcionário
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
    .then(response => {
      if (!response.ok) {
        throw new Error('Erro ao cadastrar fornecedor');
      }
      return response.json();
    })
    .then(data => {
      alert("FORNECEDOR CADASTRADO COM SUCESSO");
      console.log('Sucesso:', data);
      window.location.href = 'fornecedor.html';
    })
    .catch((error) => {
      console.error('Erro:', error);
    });
}

document.addEventListener('DOMContentLoaded', function() {
  document.getElementById('cadastroForm').addEventListener('submit', cadastraFornecedor);
});
