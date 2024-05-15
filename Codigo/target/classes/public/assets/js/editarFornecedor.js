document.addEventListener('DOMContentLoaded', async () => {
    const urlParams = new URLSearchParams(window.location.search);
    const fornecedorId = urlParams.get('id');

    if (fornecedorId) {
        console.log('ID do fornecedor:', fornecedorId); // Imprime o ID do fornecedor no console
        try {
            const fornecedor = await fetchFornecedorById(fornecedorId);
            preencherCamposDeEdicao(fornecedor);
        } catch (error) {
            console.error('Erro ao obter dados do fornecedor:', error);
            alert('Ocorreu um erro ao obter os dados do fornecedor. Por favor, tente novamente.');
        }
    } else {
        console.error('ID do fornecedor não encontrado na URL.');
    }
});

async function fetchFornecedorById(id) {
    try {
        const response = await fetch(`http://localhost:6789/fornecedor/${id}`);
        if (!response.ok) {
            throw new Error('Erro ao obter dados do fornecedor');
        }
        return await response.json();
    } catch (error) {
        console.error('Erro ao obter dados do fornecedor:', error);
        throw error;
    }
}

function preencherCamposDeEdicao(fornecedor) {
    document.getElementById('fornecedor').value = fornecedor.nome;
    document.getElementById('categoria').value = fornecedor.categoria;
    document.getElementById('celular').value = fornecedor.celular;
    document.getElementById('endereco').value = fornecedor.endereco;
}

async function editarFornecedor() {
    const idDoFornecedor = obterIdDoFornecedor();
    const nome = document.getElementById('fornecedor').value;
    const categoria = document.getElementById('categoria').value;
    const celular = document.getElementById('celular').value;
    const endereco = document.getElementById('endereco').value;

    const dadosAtualizados = {
        id: idDoFornecedor,
        nome: nome,
        categoria: categoria,
        celular: celular,
        endereco: endereco
    };

    try {
        const response = await fetch(`http://localhost:6789/fornecedor/atualizar/${idDoFornecedor}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(dadosAtualizados)
        });
        if (!response.ok) {
            throw new Error('Erro ao atualizar fornecedor');
        }
        alert('Fornecedor atualizado com sucesso!');
        // Redirecionar para fornecedor.html após atualizar
        window.location.href = 'fornecedor.html';
    } catch (error) {
        console.error('Erro ao atualizar fornecedor:', error);
        alert('Ocorreu um erro ao atualizar o fornecedor. Por favor, tente novamente.');
    }
}

async function excluirFornecedor() {
    const idDoFornecedor = obterIdDoFornecedor();

    try {
        const response = await fetch(`http://localhost:6789/fornecedor/excluir/${idDoFornecedor}`, {
            method: 'DELETE'
        });
        if (!response.ok) {
            throw new Error('Erro ao excluir fornecedor');
        }
        alert('Fornecedor excluído com sucesso!');
        // Redirecionar para fornecedor.html após excluir
        window.location.href = 'fornecedor.html';
    } catch (error) {
        console.error('Erro ao excluir fornecedor:', error);
        alert('Ocorreu um erro ao excluir o fornecedor. Por favor, tente novamente.');
    }
}

// Função para obter o ID do fornecedor da URL
function obterIdDoFornecedor() {
    const urlParams = new URLSearchParams(window.location.search);
    return urlParams.get('id');
}
