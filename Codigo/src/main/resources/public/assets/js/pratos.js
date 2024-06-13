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

            // Adiciona inputs e botões de ações
            const actionButtons = document.createElement('div');
            actionButtons.classList.add('action-buttons');

            const inputQuantidade = document.createElement('input');
            inputQuantidade.type = 'number';
            inputQuantidade.min = '1';
            inputQuantidade.placeholder = 'Qtd';
            actionButtons.appendChild(inputQuantidade);

            const buttonVender = document.createElement('button');
            buttonVender.textContent = 'Vender';
            buttonVender.classList.add('btn', 'btn-primary');
            buttonVender.onclick = () => {
                const quantidade = inputQuantidade.value;
                venderPrato(nomePrato, quantidade);
            };
            actionButtons.appendChild(buttonVender);

            const buttonExcluir = document.createElement('button');
            buttonExcluir.textContent = 'Excluir';
            buttonExcluir.classList.add('btn', 'btn-danger');
            buttonExcluir.onclick = () => excluirPrato(nomePrato);
            actionButtons.appendChild(buttonExcluir);

            card.appendChild(actionButtons);

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

// Função para vender um prato
function venderPrato(nomePrato, quantidade) {
    if (!quantidade || quantidade <= 0) {
        alert('Por favor, insira uma quantidade válida.');
        return;
    }

    if (confirm(`Tem certeza de que deseja vender ${quantidade} unidade(s) do prato "${nomePrato}"?`)) {
        fetch(`http://localhost:6789/evento/vender`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: `nomePrato=${encodeURIComponent(nomePrato)}&quantidade=${quantidade}`
        })
        .then(response => {
            if (response.ok) {
                alert('Venda registrada com sucesso.');
                carregarReceitas(); // Atualiza a lista de receitas após a venda
            } else {
                alert('Erro ao registrar a venda.');
            }
        })
        .catch(error => {
            console.error('Erro ao registrar a venda:', error);
        });
    }
}

// Função para excluir um prato
function excluirPrato(nomePrato) {
    if (confirm(`Tem certeza de que deseja excluir o prato "${nomePrato}"?`)) {
        fetch(`http://localhost:6789/evento/excluir?nomePrato=${encodeURIComponent(nomePrato)}`, {
            method: 'DELETE'
        })
        .then(response => {
            if (response.ok) {
                alert('Prato excluído com sucesso.');
                carregarReceitas(); // Atualiza a lista de receitas após a exclusão
            } else {
                alert('Erro ao excluir o prato.');
            }
        })
        .catch(error => {
            console.error('Erro ao excluir o prato:', error);
        });
    }
}

// Chamar a função principal ao carregar a página
window.onload = function () {
    carregarReceitas();

    // Adicionar evento de clique ao botão de pesquisa
    const searchButton = document.getElementById('searchButton');
    searchButton.addEventListener('click', filtrarReceitas);
};
