async function createItem(item) {
    try {
        const response = await fetch('/produto/insere', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(item),
        });

        if (response.ok) {
            const data = await response.json();
            console.log('Sucesso:', data);
            alert("Feito com sucesso");
        } else {
            throw new Error('Erro ao cadastrar o produto.');
        }
    } catch (error) {
        console.error('Erro:', error);
        alert('Ocorreu um erro ao cadastrar o produto. Por favor, tente novamente.');
    }
}

document.addEventListener('DOMContentLoaded', function() {
    const btnSalvar = document.querySelector('#btnSalvar');

    btnSalvar.onclick = async function(e) {
        e.preventDefault();

        const sProduto = document.querySelector('#m-Produto');
        const sCategoria = document.querySelector('#m-Categoria');
        const sQuantidade = document.querySelector('#m-Quantidade');
        const sFornecedor = document.querySelector('#m-Fornecedor');
        const sLote = document.querySelector('#m-Lote');
        const sData = document.querySelector('#m-Data');

        const item = {
            nome: sProduto.value,
            categoria: sCategoria.value,
            quantidade: parseInt(sQuantidade.value),
            fornecedor: sFornecedor.value,
            lote: sLote.value,
            datavalidade: sData.value
        };

        console.log(JSON.stringify(item));

        await createItem(item);

        // Limpar formulário após o envio
        sProduto.value = '';
        sCategoria.value = '';
        sQuantidade.value = '';
        sFornecedor.value = '';
        sLote.value = '';
        sData.value = '';
    };
});