$(document).ready(function () {
    saveBoard();
});


function saveBoard() {
    $(function () {
        $('#gameForm').submit(function () {
            var aliveFields = [];
            $.each($(".gameTable").find('.alive'), function () {
                aliveFields.push($(this).attr("id"));
            });

            $("#aliveFields").val(aliveFields);
            return true; // return false to cancel form action
        });
    });
}