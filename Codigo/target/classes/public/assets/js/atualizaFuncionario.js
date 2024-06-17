document.addEventListener('DOMContentLoaded', async function() {
    const urlParams = new URLSearchParams(window.location.search);
    const userId = urlParams.get('id');

    console.log("ID do usuário recebido:", userId);

    if (userId) {
        await carregarDadosUsuario(userId);
    }

    document.getElementById('atualizaForm').addEventListener('submit', async (event) => {
        await atualizaUsuario(event, userId);
    });

    document.getElementById('excluirButton').addEventListener('click', async (event) => {
        await excluirUsuario(event, userId);
    });
});

async function carregarDadosUsuario(userId) {
    try {
        const response = await fetch(`/funcionario/${userId}`);
        if (!response.ok) {
            throw new Error('Erro ao carregar dados do usuário');
        }
        const usuario = await response.json();
        document.getElementById('username').value = usuario.username;
        document.getElementById('email').value = usuario.email;
        document.getElementById('senha').value = usuario.senha;
        document.getElementById('idade').value = usuario.idade;
        document.getElementById('cpf').value = usuario.cpf;
        document.getElementById('salario').value = usuario.salario;
    } catch (error) {
        console.error('Erro ao carregar dados do usuário:', error);
        alert('Erro ao carregar dados do usuário. Por favor, tente novamente.');
    }
}

async function atualizaUsuario(event, userId) {
    event.preventDefault();

    const formData = {
        username: document.getElementById('username').value,
        email: document.getElementById('email').value,
        senha: document.getElementById('senha').value,
        idade: document.getElementById('idade').value,
        cpf: document.getElementById('cpf').value,
        salario: document.getElementById('salario').value
    };

    try {
        const response = await fetch(`/funcionario/atualizar/${userId}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(formData),
        });

        if (response.ok) {
            alert('Usuário atualizado com sucesso!');
            window.location.href = 'funcionarios.html'; // Redirecionar de volta para a página de listagem
        } else {
            throw new Error('Erro ao atualizar usuário');
        }
    } catch (error) {
        console.error('Erro na atualização:', error);
        alert('Erro ao atualizar usuário. Por favor, tente novamente.');
    }
}

async function excluirUsuario(event, userId) {
    event.preventDefault();

    if (confirm('Você tem certeza que deseja excluir este usuário?')) {
        try {
            const response = await fetch(`/funcionario/excluir/${userId}`, {
                method: 'DELETE',
                headers: {
                    'Content-Type': 'application/json',
                },
            });

            if (response.ok) {
                alert('Usuário excluído com sucesso!');
                window.location.href = 'funcionarios.html'; // Redirecionar de volta para a página de listagem
            } else {
                throw new Error('Erro ao excluir usuário');
            }
        } catch (error) {
            console.error('Erro na exclusão:', error);
            alert('Erro ao excluir usuário. Por favor, tente novamente.');
        }
    }
}
