async function atualizaFuncionario(event) {
    event.preventDefault();

    const nomeFuncionario = document.getElementById('nome').value;
    const formData = {
        nome: document.getElementById('nome').value,
        email: document.getElementById('email').value,
        senha: document.getElementById('senha').value,
        idade: parseInt(document.getElementById('idade').value, 10),
        cpf: document.getElementById('cpf').value,
        salario: parseFloat(document.getElementById('salario').value)
    };

    try {
        // Obtendo a lista de funcionários
        const response = await fetch('https://jsonserver.samaranegabriel.repl.co/funcionarios');
        const funcionarios = await response.json();

        // Encontrando o funcionário pelo nome
        const funcionario = funcionarios.find(f => f.nome === nomeFuncionario);

        if (funcionario) {
            // Atualizando o funcionário
            await fetch(`https://jsonserver.samaranegabriel.repl.co/funcionarios/${funcionario.id}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(formData),
            });

            alert('Funcionário atualizado com sucesso!');
            // Redirecionamento opcional
        } else {
            alert('Funcionário não encontrado.');
        }
    } catch (error) {
        console.error('Erro na atualização:', error);
    }
}

document.addEventListener('DOMContentLoaded', function() {
    document.getElementById('atualizaForm').addEventListener('submit', atualizaFuncionario);
});
