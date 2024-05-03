const teste = handleSearch()

function busca(){
    console.log(teste);
    // faz um for buscando o valor do nome
    
    `<div class="card">
                <div class="card-body">
                    <h3 class="card-title">${aux.nome}</h3>
                    <p class="card-text">Categoria: ${aux.categoria}</p>
                    <p class="card-text">Quantidade: ${aux.quantidade}</p>
                    <p class="card-text">Fornecedor: ${aux.fornecedor}</p>
                    <p class="card-text">Lote: ${aux.lote}</p>
                    <p class="card-text">Data: ${aux.datavencimento}</p>
                </div>
            </div>
            `
    //
}

async function handleSearch() {
    const searchTerm = document.getElementById('searchBar').value.trim().toLowerCase();

    // Obtendo a lista de alimentos
    const alimentos = await fetchAlimentos(); // Alterado para aguardar a resposta

    let str = "";
    
    alimentos.map(aux => {
        str += `<div class="card">
                <div class="card-body">
                    <h3 class="card-title">${aux.nome}</h3>
                    <p class="card-text">Categoria: ${aux.categoria}</p>
                    <p class="card-text">Quantidade: ${aux.quantidade}</p>
                    <p class="card-text">Fornecedor: ${aux.fornecedor}</p>
                    <p class="card-text">Lote: ${aux.lote}</p>
                    <p class="card-text">Data: ${aux.datavencimento}</p>
                </div>
            </div>
            `
    })

    document.getElementById('results').innerHTML = str;

    return alimentos;
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
            <div class="card">
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
        console.log('Nenhum resultado encontrado.');
    }
}