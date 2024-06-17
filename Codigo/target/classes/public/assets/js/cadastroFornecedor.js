// Função cadastra fornecedor
function cadastraFornecedor(event) {
  event.preventDefault();
  
  const formData = {
    nome: document.getElementById('fornecedor').value,
    categoria: document.getElementById('categoria').value,
    celular: formatarCelular(document.getElementById('celular').value),
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

// Função para formatar o número de celular
function formatarCelular(celular) {
  // Remove todos os caracteres não numéricos do número de celular
  const celularNumerico = celular.replace(/\D/g, '');

  // Verifica se o número de celular possui no máximo 9 dígitos
  if (celularNumerico.length > 9) {
    alert('O número de celular não pode ter mais que 9 dígitos.');
    return ''; // Retorna uma string vazia se houver erro
  }

  // Formatação para um formato específico, como DDD + número
  // Exemplo: adicionando DDD 99 ao número 123456789
  const ddd = '99'; // Substituir pelo DDD desejado
  const numeroFormatado = `${ddd}${celularNumerico}`;

  return numeroFormatado;
}

document.addEventListener('DOMContentLoaded', function() {
  document.getElementById('cadastroForm').addEventListener('submit', cadastraFornecedor);
});
