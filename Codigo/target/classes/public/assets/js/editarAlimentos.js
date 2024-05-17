document.addEventListener('DOMContentLoaded', async () => {
    const urlParams = new URLSearchParams(window.location.search);
    const produtoId = urlParams.get('id');

    if (produtoId) {
        console.log('ID do produto:', produtoId); // Imprime o ID do produto no console
        try {
            const produto = await fetchProdutoById(produtoId);
            preencherCamposDeEdicao(produto);
            preencherOpcoesFornecedor(); // Preenche as opções do campo de fornecedor
        } catch (error) {
            console.error('Erro ao obter dados do produto:', error);
            alert('Ocorreu um erro ao obter os dados do produto. Por favor, tente novamente.');
        }
    } else {
        console.error('ID do produto não encontrado na URL.');
    }
});

async function fetchProdutoById(id) {
    try {
        const response = await fetch(`http://localhost:6789/produto/${id}`);
        if (!response.ok) {
            throw new Error('Erro ao obter dados do produto');
        }
        return await response.json();
    } catch (error) {
        console.error('Erro ao obter dados do produto:', error);
        throw error;
    }
}

function preencherCamposDeEdicao(produto) {
    document.getElementById('m-Produto').value = produto.nome;
    document.getElementById('m-Categoria').value = produto.categoria;
    document.getElementById('m-Quantidade').value = produto.quantidade;
    document.getElementById('m-Lote').value = produto.lote;
    document.getElementById('m-Data').value = produto.datavalidade;
}

async function preencherOpcoesFornecedor() {
    try {
        const response = await fetch('http://localhost:6789/produto/fornecedores');
        if (!response.ok) {
            throw new Error('Erro ao obter fornecedores');
        }
        const fornecedores = await response.json();
        const selectFornecedor = document.getElementById('m-Fornecedor');
        fornecedores.forEach(fornecedor => {
            const option = document.createElement('option');
            option.value = fornecedor;
            option.textContent = fornecedor;
            selectFornecedor.appendChild(option);
        });
    } catch (error) {
        console.error('Erro ao obter fornecedores:', error);
        alert('Ocorreu um erro ao obter os fornecedores. Por favor, tente novamente.');
    }
}

async function editarProduto() {
    const idDoProduto = obterIdDoProduto();
    const nome = document.getElementById('m-Produto').value;
    const categoria = document.getElementById('m-Categoria').value;
    const quantidade = document.getElementById('m-Quantidade').value;
    const fornecedor = document.getElementById('m-Fornecedor').value;
    const lote = document.getElementById('m-Lote').value;
    const datavalidade = document.getElementById('m-Data').value;

    const dadosAtualizados = {
        id: idDoProduto,
        nome: nome,
        categoria: categoria,
        quantidade: quantidade,
        fornecedor: fornecedor,
        lote: lote,
        datavalidade: datavalidade
    };

    try {
        const response = await fetch(`http://localhost:6789/produto/atualizar/${idDoProduto}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(dadosAtualizados)
        });
        if (!response.ok) {
            throw new Error('Erro ao atualizar produto');
        }
        alert('Produto atualizado com sucesso!');
        // Redirecionar para produtos.html após atualizar
        window.location.href = 'alimentos.html';
    } catch (error) {
        console.error('Erro ao atualizar produto:', error);
        alert('Ocorreu um erro ao atualizar o produto. Por favor, tente novamente.');
    }
}

async function excluirProduto() {
    const idDoProduto = obterIdDoProduto();

    try {
        const response = await fetch(`http://localhost:6789/produto/excluir/${idDoProduto}`, {
            method: 'DELETE'
        });
        if (!response.ok) {
            throw new Error('Erro ao excluir produto');
        }
        alert('Produto excluído com sucesso!');
        // Redirecionar para produtos.html após excluir
        window.location.href = 'alimentos.html';
    } catch (error) {
        console.error('Erro ao excluir produto:', error);
        alert('Ocorreu um erro ao excluir o produto. Por favor, tente novamente.');
    }
}

// Função para obter o ID do produto da URL
function obterIdDoProduto() {
    const urlParams = new URLSearchParams(window.location.search);
    return urlParams.get('id');
}
