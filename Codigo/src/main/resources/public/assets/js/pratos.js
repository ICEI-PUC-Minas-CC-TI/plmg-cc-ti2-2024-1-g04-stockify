// Função para obter as receitas do servidor
function obterReceitasDoServidor() {
    return fetch('http://localhost:6789/receita/getAll')
        .then(response => response.json())
        .then(data => data);
}

// Função para renderizar as receitas na página HTML
function renderizarReceitas(receitas) {
    const container = document.getElementById('employeeCardContainer');
    container.innerHTML = ''; // Limpa o conteúdo atual do contêiner

    if (receitas.length === 0) {
        const mensagem = document.createElement('p');
        mensagem.textContent = 'Nenhuma receita cadastrada';
        container.appendChild(mensagem);
    } else {
        // Objeto para agrupar receitas por nome do prato
        const receitasAgrupadas = {};

        receitas.forEach(receita => {
            const nomePrato = receita.nomePrato;

            if (!receitasAgrupadas[nomePrato]) {
                receitasAgrupadas[nomePrato] = [];
            }

            receitasAgrupadas[nomePrato].push(receita);
        });

        // Itera sobre as receitas agrupadas e cria um card para cada grupo de receitas
        Object.keys(receitasAgrupadas).forEach(nomePrato => {
            const receitasDoPrato = receitasAgrupadas[nomePrato];

            const card = document.createElement('div');
            card.classList.add('employeeCard');

            const header = document.createElement('header');
            header.textContent = nomePrato;
            card.appendChild(header);

            const listaIngredientes = document.createElement('ul');
            listaIngredientes.classList.add('ingredientes-list');

            receitasDoPrato.forEach(receita => {
                const itemLista = document.createElement('li');
                itemLista.textContent = `${receita.nomeIngrediente} - Quantidade: ${receita.quantidadeIngrediente}`;
                listaIngredientes.appendChild(itemLista);
            });

            card.appendChild(listaIngredientes);
            container.appendChild(card);
        });
    }
}

// Função para filtrar as receitas com base no texto de pesquisa
function filtrarReceitas() {
    const input = document.getElementById('searchInput');
    const filtro = input.value.toUpperCase();
    const cards = document.getElementsByClassName('employeeCard');

    Array.from(cards).forEach(card => {
        const header = card.getElementsByTagName('header')[0];
        const textoHeader = header.textContent.toUpperCase();

        if (textoHeader.indexOf(filtro) > -1) {
            card.style.display = '';
        } else {
            card.style.display = 'none';
        }
    });
}

// Função principal para carregar as receitas ao carregar a página
function carregarReceitas() {
    obterReceitasDoServidor()
        .then(receitas => {
            renderizarReceitas(receitas);
        })
        .catch(error => {
            console.error('Erro ao carregar as receitas:', error);
        });
}

// Chamar a função principal ao carregar a página
window.onload = function () {
    carregarReceitas();

    // Adicionar evento de clique ao botão de pesquisa
    const searchButton = document.getElementById('searchButton');
    searchButton.addEventListener('click', filtrarReceitas);
};
