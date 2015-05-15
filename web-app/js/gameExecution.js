var timerId = 0;

function startGame() {
    if (!timerId) {
        console.log("Start Game");
        timerId = setInterval(nextStep, 200);
    }
}

function stopGame() {
    console.log("Stop Game");
    if (timerId) {
        clearInterval(timerId);
        timerId = 0;
    }
}

function nextStep() {
    console.log("Next Step");
    var gameTable = $(".gameTable");

    $.ajax({
        traditional: true,
        type: 'POST',
        data: {
            aliveFields: getSelectedFields(),
            rows: gameTable.find("#rows").val(),
            columns: gameTable.find("#columns").val(),
            generation: $("#generation").text()
        },
        url: $("#nextStepUrl").val(),
        success: function (data) {
            var jsonData = jQuery.parseJSON(data);

            cleanTable();
            setAliveFields(jsonData['alive']);
            setGeneration(jsonData['generation']);

            if (jsonData['alive'].length == 0 || !jsonData['changed']) {
                stopGame();
            }
        }
    });

}

function getSelectedFields() {
    var selectedFields = [];

    $.each($(".gameTable").find('.alive'), function () {
        selectedFields.push($(this).attr("id"));
    });
    return selectedFields;
}

function cleanTable() {
    $.each($(".gameTable").find('.alive'), function () {
        $(this).removeAttr('class');
    });
}

function setAliveFields(aliveFields) {
    var gameTable = $(".gameTable");
    $.each(aliveFields, function () {
        gameTable.find('#' + this).attr("class", 'alive');
    });
}
function setGeneration(generation) {
    $("#generation").text(generation);

}
