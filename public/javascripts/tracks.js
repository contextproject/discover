$("#gettracks").click(function () {
    widget.getSounds(function (data) {
        $.getJSON("/tracks", function (data) {
            jQuery.each(data, function (i, val) {
                $("#tracks").append("<li class='track' value='" + val.url + "'>"
                    + val.artist + " - " + val.title + "</li>");
            });
        });
    });
});

$("#tracks").on('click', '.track', function () {
    reloadWidget($(this).attr("value"));
});