const teste = handleSearch();

async function busca() {
    try {
        const termoBusca = document.getElementById('searchBar').value.trim().toLowerCase();

        // Faz a busca dos alimentos
        const alimentos = await fetchAlimentos();

        // Filtra os alimentos com base no termo de busca
        const resultados = alimentos.filter(alimento =>
            alimento.nome.toLowerCase().includes(termoBusca)
        );

        // Exibe os resultados na interface
        displayResults(resultados);
    } catch (error) {
        console.error("Ocorreu um erro na busca:", error);
    }
}

async function handleSearch() {
    try {
        // Faz a busca dos alimentos
        const alimentos = await fetchAlimentos();

        if (alimentos.length === 0) {
            // Se não houver nenhum produto cadastrado, exibe a mensagem centralizada
            document.getElementById('results').innerHTML = "<p class='no-products'>Nenhum Produto Cadastrado...</p>";
            return;
        }

        let str = "";

        // Itera sobre os alimentos e gera os cards
        alimentos.forEach(alimento => {
            str += `
                <div class="card" onclick="redirectToEditPage(${alimento.id})">
                    <div class="card-body">
                        <h3 class="card-title">${alimento.nome}</h3>
                        <p class="card-text">Categoria: ${alimento.categoria}</p>
                        <p class="card-text">Quantidade: ${alimento.quantidade}</p>
                        <p class="card-text">Fornecedor: ${alimento.fornecedor}</p>
                        <p class="card-text">Lote: ${alimento.lote}</p>
                        <p class="card-text">Data: ${alimento.datavencimento}</p>
                    </div>
                </div>
            `;
        });

        document.getElementById('results').innerHTML = str;
    } catch (error) {
        console.error("Ocorreu um erro na busca:", error);
    }
}


async function fetchAlimentos() {
    try {
        const response = await fetch('/produto/getAll', {
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
        alert('Ocorreu um erro ao retornar produto. Por favor, tente novamente.');
        throw error; // Lançar a exceção para tratamento posterior
    }
}

function displayResults(resultados) {
    if (resultados && resultados.length > 0) {
        const resultsDiv = document.getElementById('results');
        const html = resultados.map(alimento => `
            <div class="card" onclick="redirectToEditPage(${alimento.id})">
                <div class="card-body">
                    <h3 class="card-title">${alimento.nome}</h3>
                    <p class="card-text">Categoria: ${alimento.categoria}</p>
                    <p class="card-text">Quantidade: ${alimento.quantidade}</p>
                    <p class="card-text">Fornecedor: ${alimento.fornecedor}</p>
                    <p class="card-text">Lote: ${alimento.lote}</p>
                    <p class="card-text">Data: ${alimento.datavencimento}</p>
                </div>
            </div>
        `).join('');
        resultsDiv.innerHTML = html;
    } else {
        // Se não houver resultados, exibe a mensagem centralizada
        document.getElementById('results').innerHTML = "<p class='no-products'>Nenhum Produto Cadastrado...</p>";
    }
}

function redirectToEditPage(id) {
    window.location.href = `editarAlimentos.html?id=${id}`; // Adiciona o ID do produto como parâmetro na URL
}
