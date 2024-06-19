function login() {
    const email = document.getElementById('email').value;
    const senha = document.getElementById('senha').value;

    const credenciais = {
        email: email,
        senha: senha
    };

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
        if (data) {
            alert('Login bem-sucedido!');

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
        const user = userData.find(user => user.email === email);
        if (user) {a
            localStorage.setItem('username', user.username);
            localStorage.setItem('email', user.email);
            window.location.href = 'homepage.html';
        } else {
            throw new Error('Usuário não encontrado.');
        }
    })
    .catch(error => {
        alert('Erro ao fazer login: ' + error.message);
    });
}

function logout() {
    localStorage.clear();
    window.location.href = '../index.html';
}

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

function atualizarPredicao() {
    fetch('http://localhost:6789/atualizarPredicao', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    })
    .then(response => {
        if (response.ok) {
            console.log("Predição atualizada com sucesso.");
        } else {
            console.error("Erro ao atualizar predição.");
        }
    })
    .catch(error => {
        console.error("Erro ao atualizar predição: ", error);
    });
}

document.addEventListener('DOMContentLoaded', () => {
    displayUserInfo();
    atualizarPredicao();
});
