$(document).ready(function () {
    var cells = $(".gameTable td");
    $.each(cells, function () {
        var it = $(this);
        it.click(function () {

            if (it.hasClass("alive")) {
                //it.removeClass('alive');
                it.removeAttr('class');

            } else {
                it.attr("class", 'alive');
            }
        });
    });
});
