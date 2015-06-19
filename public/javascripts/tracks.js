// This file handles the search bar and recommended songs.

// The search bar
var searchBar = $("#search");
searchBar.keypress(function (e) {
    if (e.which == 13) {
        var input = searchBar.val();
        if (input.charAt(0) == "\/") {
            reloadWidget("w.soundcloud.com/tracks" + input);
        } else if (input.length == 0) {
            $("#searchList").html("");
        } else {
            search(input);
        }
        searchBar.val("");
    }
});

function search(input) {
    SC.get('/tracks', {q: input, limit: 5}, function (tracks) {
        append(tracks, $("#searchList"));
    });
}

// The list of search items
$("#searchList").on('click', '.item', function () {
    reloadWidget($(this).attr("value"));
    $("#searchList").html("");
});

// THe list of recommended tracks
$("#trackList").on('click', '.item', function () {
    reloadWidget($(this).attr("value"));
    $("#trackList").html("");
});

$(".recommend").click(function () {
    var element = $(this).attr("id");
    widget.getSounds(function () {
        if (element == "dislike") {
            dislike($.getJSON("/recommend", function (tracks) {
                reloadWidget("w.soundcloud.com/tracks/" + tracks[0].id);
                append(tracks, $("#trackList"));
            }))
        } else {
            like($.getJSON("/recommend", function (tracks) {
                append(tracks, $("#trackList"));
            }))
        }
    });
});

function like(callback) {
    widget.getCurrentSoundIndex(function (index) {
        widget.getSounds(function (sounds) {
            sendData(sounds[index], "/like", function () {
                callback();
            });
        });
    });
}

function dislike(callback) {
    widget.getCurrentSoundIndex(function (index) {
        widget.getSounds(function (sounds) {
            sendData(sounds[index], "/dislike", function () {
                callback();
            });
        });
    });
}

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
        });
    });
}

function append(tracks, element) {
    element.html("<h2>Recommended Songs!</h2>");
    jQuery.each(tracks, function (i, track) {
        SC.get('/tracks/' + track.id, function (updatedtrack) {
            element.append(
                "<li class='list_item' >" +
                "<div class='row 150% item' value=" + updatedtrack.permalink_url + ">" +
                "<div class='2u 12u'>" +
                "<span class='album-art'>" +
                "<img src=" + updatedtrack.artwork_url +
                "</span> " +
                "</div>" +
                "<div class='10u 12u content'>" +
                "<div class='user'><h6>" + updatedtrack.user.username + "</h6></div>" +
                "<div class='title'><h5>" + updatedtrack.title + "</h5></div>" +
                "</div>" +
                "</div>" +
                "</li>"
            );
        });
    });
}
