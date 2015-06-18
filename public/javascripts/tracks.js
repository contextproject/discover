// This file handles the search bar and displays the results of the search bar and recommended tracks.

$("#reload").click(function () {
    analyzeInput();
});

$("#url").keypress(function (e) {
    if (e.which == 13) {

        analyzeInput();
    }
});

$("#tracks").on('click', '.item', function () {
    reloadWidget($(this).attr("value"));
});

$("#searchList").on('click', '.item', function () {
    reloadWidget($(this).attr("value"));
    $("#searchList").html("");
});

function analyzeInput() {
    var input = $("#url").val();
    if (input.charAt(0) == "\/") {
        reloadWidget("w.soundcloud.com/tracks" + input);
    } else {
        search(input);
    }
    clearInput();
}

function clearInput() {
    $("#url").val("");
}

function search(input) {
    SC.get('/tracks', {q: input, limit: 5}, function (tracks) {
        append(tracks, $("#searchList"));
    });
}

$("#gettracks").click(function () {
    SC.get('/tracks', {q: 'buuren', limit: 5}, function (tracks) {
        append(tracks, $("#tracks"));
    });
});

// reload the widget with the url submitted in the input field
function reloadWidget(url) {
    mixSplits = null;
    // load the url in the widget
    widget.load(url, {
        auto_play: false,
        likes: true
    });
    widget.bind(SC.Widget.Events.READY, function () {
        widget.unbind(SC.Widget.Events.READY);
        var pos;
        widget.getCurrentSoundIndex(function (index) {
            pos = index;
        });
        widget.getSounds(function (sounds) {
            var message = {
                "track": sounds[pos],
                "waveform": waveform.data
            };
            sendData(message, "/request", setStartTime);
            // use the following line if you want to re-render the page.
            // window.location.href = "http://localhost:9000/tracks/" + sounds[pos].id;
        });
    });
}

function append(tracks, element) {
    element.val("");
    jQuery.each(tracks, function (i, track) {
        element.append(
            "<li class='list_item' >" +
            "<div class='row 150% item' value=" + track.permalink_url + ">" +
            "<div class='2u 12u'>" +
            "<span class='album-art'>" +
            "<img src=" + track.artwork_url +
            "</span> " +
            "</div>" +
            "<div class='10u 12u content'>" +

            "<div class='user'><h6>" + track.user.username + "</h6></div>" +
            "<div class='title'><h5>" + track.title + "</h5></div>" +

            "</div>" +
            "</div>" +
            "</li>"
        );
    });
}