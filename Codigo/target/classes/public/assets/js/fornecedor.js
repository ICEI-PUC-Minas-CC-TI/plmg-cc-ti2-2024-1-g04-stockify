const apiUrl = 'https://jsonserver.samaranegabriel.repl.co/fornecedores';

// CRUD - CREATE
const createClient = async (client) => {
    await fetch(apiUrl, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(client)
    });
}

// CRUD - READ
const readClient = async () => {
    const response = await fetch(apiUrl);
    return response.json();
}

// CRUD - UPDATE
const updateClient = async (id, client) => {
    console.log(client);
    await fetch(`${apiUrl}/${id}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(client)
    });
}

// CRUD - DELETE
const deleteClient = async (id) => {
    await fetch(`${apiUrl}/${id}`, {
        method: 'DELETE'
    });
}

// Validação de campos e limpeza de campos
const isValidFields = () => document.getElementById('form').reportValidity()

// Salvar Cliente
const saveClient = async () => {
    if (isValidFields()) {
        const client = {
            fornecedor: document.getElementById('nome').value,
            celular: document.getElementById('telefone').value,
            endereco: document.getElementById('endereco').value,
            categoria: document.getElementById('categoria').value
        };
        
        const id = document.getElementById('nome').dataset.index;
        if (id == 'new') {
            await createClient(client);
            alert("Fornecedor cadastrado com sucesso!");
        } else {
            await updateClient(id, client);
            alert("Fornecedor atualizado com sucesso!");
        }
        await updateTable();
    }
}

// Criar linha na tabela
const createRow = (client, index) => {
    const newRow = document.createElement('tr');
    newRow.innerHTML = `
        <td>${client.fornecedor}</td>
        <td>${client.categoria}</td>
        <td>${client.celular}</td>
        <td>${client.endereco}</td>

    `;
    document.querySelector('#tableClient>tbody').appendChild(newRow);
}

// Limpar tabela
const clearTable = () => {
    const rows = document.querySelectorAll('#tableClient>tbody tr');
    rows.forEach(row => row.parentNode.removeChild(row));
}

// Atualizar tabela
const updateTable = async () => {
    const dbClient = await readClient();
    clearTable();
    dbClient.forEach(createRow);
}

// Manipulador para editar e excluir
const editDelete = async (event) => {
    if (event.target.type == 'button') {
        const [action, id] = event.target.id.split('-');
        console.log(`Action: ${action}, ID: ${id}`); // Adiciona log para depuração
        if (action == 'edit') {
            console.log(`Editando cliente com ID: ${id}`); // Log ao tentar editar
            // Implemente a lógica de edição aqui. Você pode redirecionar para outra página ou usar outra estratégia.
        } else if (action == 'delete') {
            console.log(`Excluindo cliente com ID: ${id}`); // Log ao tentar excluir
            const client = (await readClient()).find(client => client.id == id);
            const response = confirm(`Deseja realmente excluir o fornecedor ${client.fornecedor}`);
            if (response) {
                await deleteClient(id);
                await updateTable();
            }
        }
    }
}

// Event Listener para a tabela
document.querySelector('#tableClient>tbody').addEventListener('click', editDelete);

// Inicialização
updateTable();
