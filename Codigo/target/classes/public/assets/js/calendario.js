var valorData, valorEvento;

function coletarDados() {
    const dataInput = document.getElementById('data').value;
    const eventoInput = document.getElementById('nome').value;

    enviarParaAPI(dataInput, eventoInput);
}

function generateUUID() {
    return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
      var r = Math.random() * 16 | 0,
          v = c === 'x' ? r : (r & 0x3 | 0x8);
      return v.toString(16);
    });
}

function enviarParaAPI(dataInput, eventoInput) {
    const url = 'http://localhost:6789/evento/insere';

    const dados = {
        data: dataInput,
        nome: eventoInput
    };

    console.log('Dados a serem enviados para a API:', dados);

    fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(dados)
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Erro na solicitação da API: ' + response.status);
        }
        return response.json();
    })
    .then(data => {
        console.log('Resposta da API:', data);
        // Lógica adicional após receber resposta da API
    })
    .catch(error => {
        console.error('Erro durante a solicitação da API:', error);
    });
}


function reloadEvents() {
    getEvents().then(res => startCalendar(res));
}

function getEvents() {
    const apiUrl = '/evento/getAll'; // Rota para buscar todos os eventos
    return fetch(apiUrl)
        .then(response => {
            if (!response.ok) {
                throw new Error('Erro ao carregar eventos: ' + response.status);
            }
            return response.json();
        })
        .catch(error => {
            console.error('Erro ao carregar eventos:', error);
            return []; // Retorna um array vazio em caso de erro
        });
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
    });

    calendar.render();
}

document.addEventListener('DOMContentLoaded', () => {
    getEvents().then(res => startCalendar(res));
});
