// DataTable initialization
$(".data-table").DataTable({
    responsive: true,
    columnDefs: [
        { orderable: false, targets: 'no-sort' }
    ],
    language: {
        info: 'Página _PAGE_ de _PAGES_',
        infoEmpty: 'Nenhum registros encontrado',
        infoFiltered: '(Filtrado de _MAX_ registros)',
        lengthMenu: 'Mostrando _MENU_ por página',
        zeroRecords: 'Nenhum registro encontrado.',
        thousands: ",",
        loadingRecords: "Carregando...",
        processing: "Aguarde...",
        search: "Buscar:",
        paginate: {
            first: "Primeira",
            last: "Última",
            next: "Próxima",
            previous: "Anterior"
        },
        aria: {
            orderable: "Ordenado por essa coluna",
            orderableReverse: "Ordenado por essa coluna em ordem reversa"
        },

    }
});

//métodos que trata o select2
//Select2 initialization
$(".select2").select2({
    theme: "bootstrap-5",
    width: '100%',
    allowClear: true,
    placeholder: "Selecione uma opção",
    searchInputPlaceholder: "Buscar",
    language: {
        "noResults": function () {
            return "Sem resultados.";
        }
    }
});

$('.select2-multi').select2({
    multiple: true,
    closeOnSelect: false,
    theme: 'bootstrap-5',
    width: '100%',
    placeholder: "Selecione uma opção",
    searchInputPlaceholder: "Buscar",
    language: {
        "noResults": function () {
            return "Sem resultados.";
        }
    }
});
//fim - métodos que trata o select2
