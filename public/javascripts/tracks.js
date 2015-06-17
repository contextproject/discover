$("#recommend").click(function () {
    widget.getSounds(function (data) {
        $.getJSON("/recommend", function (data) {
            $("#tracks").html("");
            $("#tracks").append("<tr> <th>Title</th> <th>Genre</th> <th>Score</th> </tr>");
            jQuery.each(data, function (i, val) {
                $("#tracks").append("<tr class=tracktable value= " + val.id + ">" +
                    "<td>" + val.title + "</td> " +
                    "<td>" + val.genre + "</td> " +
                    "<td>" + val.score + "</td> " +
                    "</tr>");
            });
        });
    });
});


$("#tracks").on('click', '.tracktable', function () {
    reloadWidget("w.soundcloud.com/tracks/" + $(this).attr("value"));
});

function recommendAuto() {
        $.getJSON("/recommend", function (data) {
            var first = data[0];
            console.log(data[0]);
            reloadWidget("w.soundcloud.com/tracks/" + first["id"]);
        })
};