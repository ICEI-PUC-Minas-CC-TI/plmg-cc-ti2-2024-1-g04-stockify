var valorData, valorEvento;

function coletarDados() {
    // Obter o valor do input de data
    valorData = document.getElementById('dataInput').value;
    valorEvento = document.getElementById('eventoInput').value;

    enviarParaAPI(valorEvento, valorData)
}

function generateUUID() {
    return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
      var r = Math.random() * 16 | 0,
          v = c === 'x' ? r : (r & 0x3 | 0x8);
      return v.toString(16);
    });
  }

function enviarParaAPI(evento, data) {
    var dadosParaEnviar = {
        id: generateUUID(),
        title: evento,
        start: data
    };

    // URL da sua API
    const apiUrl = 'https://jsonserver.samaranegabriel.repl.co/events';

    // Opções da solicitação
    var options = {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(dadosParaEnviar),
    };

    // Fazer a solicitação usando fetch
    fetch(apiUrl, options)
        .then(response => {
            if (!response.ok) {
                throw new Error('Erro na solicitação da API: ' + response.status);
            }
            reloadEvents()
            // return response.json();
        })
        .catch(error => {
            console.error('Erro durante a solicitação da API:', error);
            // Lidar com erros durante a solicitação
        });
}


function reloadEvents(){
    getEvents().then(res => startCalendar(res))

}
function getEvents() {
    return fetch('https://jsonserver.samaranegabriel.repl.co/events')
        .then(response => response.json())
        .catch(error => console.error('Erro ao carregar eventos:', error));
}

function startCalendar(data) {
    var calendarEl = document.getElementById('calendar');
    var calendar = new FullCalendar.Calendar(calendarEl, {
        initialView: 'dayGridMonth',
        initialDate: new Date(),
        headerToolbar: {
            left: 'prev,next today',
            center: 'title',
            right: 'dayGridMonth,timeGridWeek,timeGridDay'
        },
        locale: 'pt-br',
        events: data
    }
    );
    calendar.render();
}

document.addEventListener('DOMContentLoaded', () => {
    getEvents().then(res => startCalendar(res))
});






