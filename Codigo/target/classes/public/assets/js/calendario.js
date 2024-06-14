document.addEventListener('DOMContentLoaded', function() {
    var calendarEl = document.getElementById('calendar');
    var calendar = new FullCalendar.Calendar(calendarEl, {
        initialView: 'dayGridMonth',
        headerToolbar: {
            left: 'prev,next today',
            center: 'title',
            right: 'dayGridMonth,timeGridWeek,timeGridDay'
        },
        events: function(fetchInfo, successCallback, failureCallback) {
            fetch('http://localhost:6789/evento/getAll')
                .then(response => response.json())
                .then(data => {
                    let events = data.map(evento => ({
                        id: evento.id,
                        title: evento.nomeEvento,
                        start: luxon.DateTime.fromISO(evento.data).toISODate() // Convertendo corretamente a data
                    }));
                    successCallback(events);
                })
                .catch(error => {
                    console.error('Erro ao carregar eventos:', error);
                    failureCallback(error);
                });
        },
        eventClick: function(info) {
            abrirModalEdicao(info.event);
        },
        // Configurações para exibir slots de 1 hora nos modos semana e dia
        slotDuration: '00:30:00', // Duração dos slots de 1 hora
        slotMinTime: '07:00:00', // Horário inicial dos slots (8:00 AM)
        slotMaxTime: '24:00:00', // Horário final dos slots (6:00 PM)
    });
    calendar.render();
});

function coletarDados() {
    const dataInput = document.getElementById('data').value;
    const eventoInput = document.getElementById('nome').value;

    enviarParaAPI(dataInput, eventoInput);
}

function enviarParaAPI(dataInput, eventoInput) {
    const url = 'http://localhost:6789/evento/insere';

    const dados = {
        data: dataInput,
        nomeEvento: eventoInput
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
        alert('Evento criado com sucesso!');
        location.reload(); // Recarrega a página para atualizar o calendário
    })
    .catch(error => {
        console.error('Erro durante a solicitação da API:', error);
        alert('Erro ao criar o evento.');
    });
}

function abrirModalEdicao(evento) {
    // Preenche os campos do modal com os dados do evento selecionado
    document.getElementById('editNomeEvento').value = evento.title;
    document.getElementById('editDataEvento').value = evento.startStr;

    // Armazena o ID do evento em um campo oculto
    document.getElementById('editEventoId').value = evento.id;

    // Abre o modal de edição
    var modal = new bootstrap.Modal(document.getElementById('editarEventoModal'));
    modal.show();
}

function salvarEdicaoEvento() {
    const idEvento = document.getElementById('editEventoId').value;
    const nomeEvento = document.getElementById('editNomeEvento').value;
    const dataEvento = document.getElementById('editDataEvento').value;

    const url = `http://localhost:6789/evento/atualizar/${idEvento}`;

    const dados = {
        nomeEvento: nomeEvento,
        data: dataEvento
    };

    console.log('Dados a serem enviados para a API:', dados);

    fetch(url, {
        method: 'PUT',
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
        alert('Evento editado com sucesso!');
        location.reload(); // Recarrega a página para atualizar o calendário
    })
    .catch(error => {
        console.error('Erro durante a solicitação da API:', error);
        alert('Erro ao editar o evento.');
    });

    // Fecha o modal após salvar as alterações
    var modal = bootstrap.Modal.getInstance(document.getElementById('editarEventoModal'));
    modal.hide();
}

function confirmarExclusao() {
    var modal = new bootstrap.Modal(document.getElementById('confirmarExclusaoModal'));
    modal.show();
}

function excluirEvento() {
    const idEvento = document.getElementById('editEventoId').value;

    const url = `http://localhost:6789/evento/excluir/${idEvento}`;

    fetch(url, {
        method: 'DELETE'
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Erro na solicitação da API: ' + response.status);
        }
        return response.json();
    })
    .then(data => {
        console.log('Resposta da API:', data);
        alert('Evento excluído com sucesso!');
        location.reload(); // Recarrega a página para atualizar o calendário
    })
    .catch(error => {
        console.error('Erro durante a solicitação da API:', error);
        alert('Erro ao excluir o evento.');
    });

    // Fecha o modal de confirmação de exclusão
    var modal = bootstrap.Modal.getInstance(document.getElementById('confirmarExclusaoModal'));
    modal.hide();

    // Fecha o modal de edição também, se estiver aberto
    var editModal = bootstrap.Modal.getInstance(document.getElementById('editarEventoModal'));
    if (editModal) {
        editModal.hide();
    }
}
