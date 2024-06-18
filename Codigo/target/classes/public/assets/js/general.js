function login() {
    const email = document.getElementById('email').value;
    const senha = document.getElementById('senha').value;

    // Construir objeto com credenciais
    const credenciais = {
        email: email,
        senha: senha
    };

    // Enviar as credenciais para o endpoint de login no seu backend
    fetch('http://localhost:6789/login/procura', {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(credenciais)
    })
    .then(response => {
        if (response.ok) {
            return response.json();
        } else {
            throw new Error('Email ou senha incorretos.');
        }
    })
    .then(data => {
        // Se o login for bem-sucedido, buscar informações adicionais do usuário
        if (data) {
            alert('Login bem-sucedido!');

            // Fazer outra chamada para obter as informações do usuário
            return fetch('http://localhost:6789/funcionarios/getAll', {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json'
                }
            });
        } else {
            console.log("Deu algum erro");
        }
    })
    .then(response => response.json())
    .then(userData => {
        // Encontrar o usuário logado na lista de funcionários
        const user = userData.find(user => user.email === email);
        if (user) {
            localStorage.setItem('username', user.username);
            localStorage.setItem('email', user.email);
            window.location.href = 'homepage.html';
        } else {
            throw new Error('Usuário não encontrado.');
        }
    })
    .catch(error => {
        // Se ocorrer um erro durante o login ou busca de informações
        alert('Erro ao fazer login: ' + error.message);
    });
}

function logout() {
    localStorage.clear();
    window.location.href = '../index.html';
}

// Função para exibir informações do usuário
function displayUserInfo() {
    const usernameDisplay = document.getElementById('usernameDisplay');
    const emailDisplay = document.getElementById('emailDisplay');

    const username = localStorage.getItem('username');
    const email = localStorage.getItem('email');

    if (username) {
        usernameDisplay.textContent = username;
    }

    if (email) {
        emailDisplay.textContent = email;
    }
}

// Chame a função para exibir as informações do usuário quando a página carregar
document.addEventListener('DOMContentLoaded', displayUserInfo);

