$("#gettracks").click(function () {
    widget.getSounds(function (data) {
        $.getJSON("/tracks", function (data) {
            jQuery.each(data, function (i, val) {
                $("#tracks").append("<li class='track' value='" + val.id + "'>"
                    + val.artist + " - " + val.title + " - " + val.genre +"</li>");
            });
        });
    });
});

$("#recommend").click(function () {
    widget.getSounds(function (data) {
        $.getJSON("/tracks", function (data) {
            jQuery.each(data, function (i, val) {
                $("#tracks").append("<li class='track' value='" + val.id + "'>"
                    + val.artist + " - " + val.title + " - " + val.genre +"</li>");
            });
        });
    });
});


$("#tracks").on('click', '.track', function () {
    reloadWidget("w.soundcloud.com/tracks/" + $(this).attr("value"));
});