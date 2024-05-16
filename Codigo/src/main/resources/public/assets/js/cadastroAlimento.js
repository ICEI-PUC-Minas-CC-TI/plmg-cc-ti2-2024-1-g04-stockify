async function createItem(item) {
    try {
        const response = await fetch('/produto/insere', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(item),
        });

        if (response.ok) {
            const data = await response.json();
            console.log('Sucesso:', data);
            alert("Feito com sucesso");
            // Redirecionar para a página de estoque após sucesso
            window.location.href = 'alimentos.html'; // Certifique-se de que o caminho está correto
        } else {
            throw new Error('Erro ao cadastrar o produto.');
        }
    } catch (error) {
        console.error('Erro:', error);
        alert('Ocorreu um erro ao cadastrar o produto. Por favor, tente novamente.');
    }
}

async function fetchFornecedores() {
    try {
        const response = await fetch('/fornecedor/getAll');
        if (response.ok) {
            const fornecedores = await response.json();
            const fornecedorSelect = document.querySelector('#m-Fornecedor');
            fornecedores.forEach(fornecedor => {
                const option = document.createElement('option');
                option.value = fornecedor.nome; // Usar o nome como valor
                option.textContent = fornecedor.nome; // Mostrar o nome no texto
                fornecedorSelect.appendChild(option);
            });
        } else {
            throw new Error('Erro ao buscar fornecedores.');
        }
    } catch (error) {
        console.error('Erro:', error);
        alert('Ocorreu um erro ao buscar os fornecedores. Por favor, tente novamente.');
    }
}

document.addEventListener('DOMContentLoaded', async function() {
    await fetchFornecedores(); // Carrega os fornecedores ao carregar a página

    const btnSalvar = document.querySelector('#btnSalvar');

    btnSalvar.onclick = async function(e) {
        e.preventDefault();

        const sProduto = document.querySelector('#m-Produto');
        const sCategoria = document.querySelector('#m-Categoria');
        const sQuantidade = document.querySelector('#m-Quantidade');
        const sFornecedor = document.querySelector('#m-Fornecedor');
        const sLote = document.querySelector('#m-Lote');
        const sData = document.querySelector('#m-Data');

        const item = {
            nome: sProduto.value,
            categoria: sCategoria.value,
            quantidade: parseInt(sQuantidade.value),
            fornecedor: sFornecedor.value, // Agora isso será o nome do fornecedor
            lote: sLote.value,
            datavalidade: sData.value
        };

        console.log(JSON.stringify(item));

        await createItem(item);

        // Limpar formulário após o envio
        sProduto.value = '';
        sCategoria.value = '';
        sQuantidade.value = '';
        sFornecedor.value = '';
        sLote.value = '';
        sData.value = '';
    };
});