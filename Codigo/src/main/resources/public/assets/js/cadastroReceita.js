document.addEventListener("DOMContentLoaded", function() {
    // Carregar os produtos ao carregar a página
    fetchProdutos();
});

function fetchProdutos() {
    fetch("/produto/getAll")
        .then(response => response.json())
        .then(data => {
            const selectProduto = document.getElementById("productName");
            selectProduto.innerHTML = ""; // Limpar o select antes de adicionar os novos produtos
            data.forEach(produto => {
                const option = document.createElement("option");
                option.value = produto.id;
                option.textContent = produto.nome;
                option.dataset.estoque = produto.estoque; // Adicionei um atributo "data-estoque" ao option com a quantidade em estoque
                selectProduto.appendChild(option);
            });
        })
        .catch(error => console.error("Erro ao buscar produtos:", error));
}

function addIngredient() {
    const selectProduto = document.getElementById("productName");
    const produtoId = selectProduto.value;
    const produtoNome = selectProduto.options[selectProduto.selectedIndex].textContent;

    if (produtoId.trim() === "") {
        alert("Por favor, selecione um produto.");
        return;
    }

    // Obter a quantidade disponível em estoque
    const estoque = parseInt(selectProduto.options[selectProduto.selectedIndex].dataset.estoque);

    // Verificar se o estoque é maior que 0
    if (estoque <= 0) {
        alert("Não há estoque disponível para este produto.");
        return;
    }

    const ingredientList = document.getElementById("ingredientList");
    if (!ingredientList) {
        console.error("Elemento com ID 'ingredientList' não encontrado.");
        return;
    }

    const ingredientRow = document.createElement("div");
    ingredientRow.classList.add("ingredient-row");

    const ingredientInput = document.createElement("input");
    ingredientInput.type = "text";
    ingredientInput.name = "ingredient";
    ingredientInput.value = produtoNome;
    ingredientInput.readOnly = true; // Alteração aqui para readonly

    const quantidadeLabel = document.createElement("label");
    quantidadeLabel.textContent = "Quantidade:";
    const quantidadeInput = document.createElement("input");
    quantidadeInput.type = "number";
    quantidadeInput.name = "quantidade";
    quantidadeInput.value = "1"; // Valor padrão é 1
    quantidadeInput.min = "1"; // Quantidade mínima permitida
    quantidadeInput.max = estoque.toString(); // Quantidade máxima permitida
    quantidadeInput.required = true; // Campo de quantidade é obrigatório

    quantidadeInput.addEventListener("change", function() {
        if (parseInt(quantidadeInput.value) > estoque) {
            alert("A quantidade selecionada excede o estoque disponível.");
            quantidadeInput.value = estoque.toString();
        }
    });

    const removeButtonContainer = document.createElement("div");
    removeButtonContainer.classList.add("remove-button-container");

    const removeButton = document.createElement("button");
    removeButton.type = "button";
    removeButton.textContent = "-";
    removeButton.classList.add("btn", "bg-dark", "text-white", "mb-4"); // Adicionar as classes CSS ao botão "-"
    removeButton.style.width = "50%"; // Ajustar a largura do botão para corresponder ao botão "+"

    removeButton.addEventListener("click", function() {
        ingredientRow.remove();
    });

    removeButtonContainer.appendChild(removeButton);

    ingredientRow.appendChild(ingredientInput);
    ingredientRow.appendChild(quantidadeLabel);
    ingredientRow.appendChild(quantidadeInput);
    ingredientRow.appendChild(removeButtonContainer);
    ingredientList.appendChild(ingredientRow);
}

function salvarDados() {
    const nPrato = document.getElementById("nPrato").value.trim();

    if (nPrato === "") {
        alert("Por favor, insira o nome do prato.");
        return;
    }

    const ingredientes = [];
    const quantidades = [];

    const ingredientRows = document.querySelectorAll(".ingredient-row");
    ingredientRows.forEach(row => {
        const ingrediente = row.querySelector("input[name='ingredient']").value.trim();
        const quantidade = parseInt(row.querySelector("input[name='quantidade']").value.trim());
        ingredientes.push(ingrediente);
        quantidades.push(quantidade);
    });

    const data = {
        "nomePrato": nPrato,
        "ingredientes": ingredientes,
        "quantidades": quantidades
    };

    fetch("/receita/insere", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(data)
    })
    .then(response => {
        if (response.ok) {
            alert("Receita cadastrada com sucesso!");
            document.getElementById("nPrato").value = "";
            document.getElementById("ingredientList").innerHTML = "";
            fetchProdutos();
        } else {
            throw new Error("Erro ao cadastrar receita: " + response.status);
        }
    })
    .catch(error => console.error(error));
}

