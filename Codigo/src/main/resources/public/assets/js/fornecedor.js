document.addEventListener('DOMContentLoaded', () => {
    const cadastrarFornecedor = document.getElementById('cadastrarFornecedor');
    const tableClientBody = document.querySelector('#tableClient tbody');

    if (cadastrarFornecedor && tableClientBody) {
        handleSearch();
    } else {
        console.error("Elementos 'cadastrarFornecedor' ou 'tableClientBody' não encontrados no DOM.");
    }
});

async function handleSearch() {
    // Obtendo a lista de fornecedores
    const fornecedores = await fetchFornecedores();
    displayResults(fornecedores);
    return fornecedores;
}

async function fetchFornecedores() {
    try {
        const response = await fetch('http://localhost:6789/fornecedor/getAll', {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
            }
        });
        if (!response.ok) {
            throw new Error('Erro ao obter dados do servidor');
        }
        return await response.json();
    } catch (error) {
        console.error('Erro:', error);
        alert('Ocorreu um erro ao retornar os fornecedores. Por favor, tente novamente.');
        throw error;
    }
}

function displayResults(resultados) {
    const tableClientBody = document.querySelector('#tableClient tbody');
    if (!tableClientBody) {
        console.error("Elemento 'tableClientBody' não encontrado no DOM.");
        return;
    }

    if (resultados && resultados.length > 0) {
        let html = resultados.map(fornecedor => `
            <tr data-id="${fornecedor.id}" class="fornecedor-row"> <!-- Adicionando o ID do fornecedor como atributo data-id -->
                <td>${fornecedor.nome}</td>
                <td>${fornecedor.categoria}</td>
                <td>${fornecedor.celular}</td>
                <td>${fornecedor.endereco}</td>
            </tr>
        `).join('');
        tableClientBody.innerHTML = html;

        // Adicionando evento de clique para cada linha da tabela
        const rows = document.querySelectorAll('.fornecedor-row');
        rows.forEach(row => {
            row.addEventListener('click', () => {
                const fornecedorId = row.dataset.id;
                redirecionarParaEditarFornecedor(fornecedorId);
            });
        });
    } else {
        tableClientBody.innerHTML = '<tr><td colspan="4">Nenhum Fornecedor cadastrado.</td></tr>';
        tableClientBody.style.pointerEvents = 'none'; // Desativa o evento de clique na tabela
        tableClientBody.style.cursor = 'default'; // Altera o cursor para o padrão
    }
}

function redirecionarParaEditarFornecedor(id) {
    window.location.href = `editarFornecedor.html?id=${id}`;
}

document.querySelector('#tableClient>tbody').addEventListener('click', (event) => {
    const row = event.target.closest('tr');
    if (row) {
        const fornecedorId = row.dataset.id; // Esta linha imprime o ID do fornecedor no console
        window.location.href = `editarFornecedor.html?id=${fornecedorId}`;
    }
});
