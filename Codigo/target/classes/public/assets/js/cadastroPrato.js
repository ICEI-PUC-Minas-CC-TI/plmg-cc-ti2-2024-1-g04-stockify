function addIngredient() {
    var productName = document.getElementById("productName").value;
    
    if (productName.trim() === "") {
      alert("Por favor, insira um nome de ingrediente.");
      return;
    }
    
    var ingredientList = document.getElementById("ingredientList");
    var ingredientInput = document.createElement("input");
    ingredientInput.type = "text";
    ingredientInput.name = "ingredient";
    ingredientInput.value = productName;
    ingredientInput.readOnly = true; // Alteração aqui para readonly
    ingredientList.appendChild(ingredientInput);
    ingredientList.appendChild(document.createElement("br"));
    document.getElementById("productName").value = ""; 
  }
  
  function salvarDados() {
    var nPrato = document.getElementById("nPrato").value;
    
    if (nPrato.trim() === "") {
      alert("Por favor, insira o nome do prato.");
      return;
    }
    
    var ingredientList = document.getElementById("ingredientList");
    var ingredients = [];
    var inputs = ingredientList.getElementsByTagName("input");
    for (var i = 0; i < inputs.length; i++) {
      ingredients.push(inputs[i].value);
    }
    
    var data = {
      "prato": nPrato,
      "ingredientes": ingredients
    };
    
    console.log(JSON.stringify(data));
  }