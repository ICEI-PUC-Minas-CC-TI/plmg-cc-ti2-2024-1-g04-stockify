

function login() {
    const email = document.getElementById('email').value;
    const senha = document.getElementById('senha').value;

    // Construir objeto com credenciais
    const credenciais = {
        email: email,
        senha: senha
    };

    // Enviar as credenciais para o endpoint de login no seu backend
    fetch('/login', {
        method: 'POST',
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
        // Se o login for bem-sucedido, armazenar token e outras informações relevantes
        localStorage.setItem('token', data.token);
        localStorage.setItem('usuario', JSON.stringify(data.usuario));
 
        alert('Login bem-sucedido!');
 
        // Redirecionar para a página inicial ou outra página apropriada
        window.location.href = 'homepage.html';
    })
    .catch(error => {
        // Se ocorrer um erro durante o login
        alert('Erro ao fazer login: ' + error.message);
    });
}



function logout() {
    localStorage.clear();
    window.location.href = '../index.html';
    console.log("LOCALSTORAGE LIMPO");
}
