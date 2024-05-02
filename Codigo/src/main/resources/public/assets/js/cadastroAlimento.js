document.addEventListener('DOMContentLoaded', function() {
    const sProduto = document.querySelector('#m-Produto');
    const sCategoria = document.querySelector('#m-Categoria');
    const sQuantidade = document.querySelector('#m-Quantidade');
    const sFornecedor = document.querySelector('#m-Fornecedor');
    const sLote = document.querySelector('#m-Lote');
    const sData = document.querySelector('#m-Data');
    const btnSalvar = document.querySelector('#btnSalvar');

    btnSalvar.onclick = async e => {
        e.preventDefault();

        if (sProduto.value == '' || sCategoria.value == '' || sQuantidade.value == '' || sFornecedor.value == '' || sLote.value =='' || sData.value == '') {
            return;
        }

        const item = {
            nome: sProduto.value,
            categoria: sCategoria.value,
            quantidade: sQuantidade.value,
            fornecedor: sFornecedor.value,
            lote: sLote.value,
            data: sData.value
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

    async function createItem(item) {
        await fetch('https://jsonserver.samaranegabriel.repl.co/alimentos', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(item),
        });
    }
});
