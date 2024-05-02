var db = {
  "Perguntaserespostas": [
      {
          "pergunta": "Como adiciono um produto?",
          "resposta": "Para adicionar um produto você deve clicar em 'inventário', 'adicionar produto' e por fim preencher as informações do mesmo, finalizando a operação.",
          "id": 1
      },
      {
          "pergunta": "Como excluir um produto?",
          "resposta": "Para excluir um produto você deve clicar em 'inventário', clique no produto que deseja excluir, clique em 'editar' e em 'deletar'.",
          "id": 2
      },
      /*
      {
          "pergunta": "Como adicionar um fornecedor?",
          "resposta": "Para adicionar um fornecedor, clique em 'inventário', 'fornecedores' e em 'adicionar fornecedor'.",
          "id": 3
      }
      */
      // ... (outras perguntas e respostas)
  ],
}
//-------------------------------------------------------------------------------------//
  
  
  const chatBox = document.getElementById("chat-box");
  const messageInput = document.getElementById("message-input");
  
  function addMessage(content, sender, isClickable = false) {
    const message = document.createElement("div");
    message.classList.add("message", sender);
  
    if (isClickable) {
      message.classList.add("clickable");
      message.onclick = function () {
        // Ao clicar na mensagem, exibir a resposta correspondente
        const questionId = content.id;
        addMessage(content.pergunta, "sent");
        setTimeout(function () {
          addMessage(getAnswerById(questionId), "received");
        }, 1000); // Intervalo de 1000 milissegundos
        // Remova as mensagens clicáveis após escolher uma
        removeClickableMessages();
      };
    }
  
    const messageText = document.createElement("div");
    messageText.classList.add("message-text");
    messageText.textContent = content.pergunta || content; // Use a pergunta se for um objeto, senão use o conteúdo normal
    message.appendChild(messageText);
  
    chatBox.appendChild(message);
  
    // Rolando para a última mensagem
    chatBox.scrollTop = chatBox.scrollHeight;
  }
  
  function getAnswerById(questionId) {
    // Encontre a resposta correspondente com base no ID da pergunta
    const question = db.Perguntaserespostas.find(item => item.id === parseInt(questionId));
    return question ? question.resposta : "Desculpe, não encontramos uma resposta para essa pergunta.";
  }
  
  function removeClickableMessages() {
    // Remover todas as mensagens clicáveis
    const clickableMessages = document.querySelectorAll(".clickable");
    clickableMessages.forEach(message => message.remove());
  }
  
  function sendMessage() {
    const messageText = messageInput.value;
    if (messageText.trim() !== "") {
      addMessage(messageText, "sent");
  
      // Simulando uma resposta recebida após um atraso
      setTimeout(function () {
        addMessage("Oi! Nosso funcionamento é de 6:00 às 18:00 de segunda à sexta. Enquanto conectamos você ao nosso atendente, preparamos algumas dúvidas frequentes para te ajudar o mais rápido possível😉!", "received");
  
        // Após exibir a resposta, adicionar perguntas clicáveis
        setTimeout(function () {
          addClickableQuestions();
        }, 1000); // Intervalo de 1000 milissegundos
      }, 1000);
  
      messageInput.value = "";
    }
  }
  
  function addClickableQuestions() {
    // Adicionar perguntas clicáveis com base nas perguntas
    db.Perguntaserespostas.forEach(item => {
      addMessage(item, "sent", true);
    });
  }
  
  // Rolar para a última mensagem quando a página carrega
  window.onload = function () {
    chatBox.scrollTop = chatBox.scrollHeight;
  };
  