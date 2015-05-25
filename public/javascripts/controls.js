// Soundcloud widget
var widget = SC.Widget(document.getElementById("sc-widget"));

// if the reload button is clicked on, call the reloadWidget function
$("#reload").click(function () {
    reloadWidget();
});
// if the url submit is entered, call the reloadWidget function
$("#url").keypress(function (e) {
    if (e.which == 13) {
        reloadWidget();
    }
});
// reload the widget with the url submitted in the input field
function reloadWidget() {
    // load the url in the widget
    widget.load($("#url").val(), {auto_play: false});

    widget.bind(SC.Widget.Events.READY, function () {
        widget.unbind(SC.Widget.Events.READY);
        var pos;
        widget.getCurrentSoundIndex(function (index) {
            pos = index;
        });
        widget.getSounds(function (sounds) {
            window.location.href = "http://localhost:9000/tracks/" + sounds[pos].id;
        });
    });

    // empty the input field
    $("#url").val("");
}

// change the volume of the widget
$("#volume").on("input change", function() {
    widget.setVolume(parseInt($('#volume').val()));
});

// play the snippet of the song
var snipWin = 10000.00;
var songStart = parseFloat(start) - (snipWin / 2);
var songEnd = songStart + snipWin;

$("#preview").click(function() {
    widget.bind(SC.Widget.Events.READY, function () {
        if (widget.isPaused(function (paused) {
                if (paused) {
                    widget.play();
                }
            }))
            widget.bind(SC.Widget.Events.PLAY, function () {
                widget.seekTo(songStart);
                widget.unbind(SC.Widget.Events.PLAY);
                widget.unbind(SC.Widget.Events.READY);
            });
    });

    widget.bind(SC.Widget.Events.PLAY_PROGRESS, function () {
        widget.getPosition(function (position) {
            if (position > songEnd) {
                widget.pause();
                widget.unbind(SC.Widget.Events.PLAY_PROGRESS);
            }
        });
    });
});

// load the widget with a random song
$("#rand").click(function () {
    window.location.href = "http://localhost:9000/random";
});

// select the previous song if present
$("#prev").click(function() {
    widget.next();
});

// select the next song if present
$("#next").click(function() {
    widget.prev();
});

// clear every event on the widget, used for debugging purposes.
$("#clear").click(function() {
    widget.unbind(SC.Widget.Events.PLAY);
    widget.unbind(SC.Widget.Events.READY);
});

// connect with Soundcloud
$("#connect").click(function() {
    if (SC.accessToken() == null) {
        SC.connect(function() {
            SC.get('/me', function(me) {
                alert("hoi");
                widget.load("api.soundcloud.com/users/" + me.id + "/favorites", {});
                $("connect").val("Disconnect");
            });
        });
    } else {
        alert("hee");
        SC.accessToken(null);
        $("connect").val("Connect");
    }
});
