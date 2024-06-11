function obterPratosDoServidor() {
    return fetch('http://localhost:6789/prato/getAll')
        .then(response => response.json())
        .then(data => data);
}


function renderizarPratos(pratos) {
    const container = document.getElementById('employeeCardContainer');
    container.innerHTML = ''; 

    if (pratos.length === 0) {
        const mensagem = document.createElement('p');
        mensagem.textContent = 'Nenhum prato cadastrado';
        container.appendChild(mensagem);
    } else {
        pratos.forEach(prato => {
            const card = document.createElement('div');
            card.classList.add('employeeCard');

            const header = document.createElement('header');
            header.textContent = prato.nome;

            const ingredientes = document.createElement('p');
            ingredientes.textContent = `Ingredientes: ${prato.ingredientes.join(', ')}`;

            const quantidades = document.createElement('p');
            quantidades.textContent = `Quantidades: ${prato.quantidades.join(', ')}`;

            card.appendChild(header);
            card.appendChild(ingredientes);
            card.appendChild(quantidades);

            container.appendChild(card);
        });
    }
}

function carregarPratos() {
    obterPratosDoServidor()
        .then(pratos => {
            renderizarPratos(pratos);
        })
        .catch(error => {
            console.error('Erro ao carregar os pratos:', error);
        });
}

window.onload = carregarPratos;
