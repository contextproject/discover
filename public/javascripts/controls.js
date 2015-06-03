// Soundcloud widget
var widget = SC.Widget(document.getElementById("sc-widget"));
var mixSplits, waveform;
var snipWin = 5000.00;
var splitPointer = -1;

$("#current").click(function () {
    $widget.bind(SC.Widget.Events.READY, function () {
        // get information about currently playing sound
        $widget.getSounds(function (currentSound) {
            console.log(currentSound[0]);
            var id = currentSound[0].id;
            console.log(id);
        });
    });
});

// Prepare all the data to be sent when the widget is ready
widget.bind(SC.Widget.Events.READY, function() {
	waveform = new Waveform({
		container : document.getElementById("sc-widget")
	});
	widget.getSounds(function(sounds) {
		waveform.dataFromSoundCloudTrack(sounds[0]);
	});
	widget.unbind(SC.Widget.Events.READY);
});

$(window).load(function() {
    $('#joyRideTipContent').joyride({
        autoStart : true,
        cookieMonster : true,
        modal:true,
        expose:true

    });
});

// The method is used to send Data to the server
function sendData(data, url, callback) {
    if (data !== undefined) {
        $.ajax({
            type: "POST",
            dataType: "json",
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            url: url,
            success: function (data) {
                callback(data.response);
            }
        });
    } else {
        console.log("Object is not usable, given data is undefined");
        console.log(data.length > 0);
    }
}

//event for disliking a song
$("#dislike").click(function () {
    if (SC.accessToken() != null) {
        widget.getCurrentSoundIndex(function (index) {
            widget.getSounds(function (sounds) {
                sendData(sounds[index], "/userDislike", function () {
                });
                SC.delete('/me/favorites/' + sounds[index].id);
            });
        });
    }
});

// event for liking a song
$("#like").click(function () {
    if (SC.accessToken() == null) {
        console.log("null");
        SC.connect(function () {
            like();
        });
    } else {
        console.log("not null");
        like();
    }
});

// like the current song
function like() {
    widget.getCurrentSoundIndex(function (index) {
        widget.getSounds(function (sounds) {
            sendData(sounds[index], "/userLike", function () {
            });
            SC.put('/me/favorites/' + sounds[index].id);
        });
    });
}

//select the next song if present
$("#next").click(function () {
    if (mixSplits != null && mixSplits != undefined) {
        splitPointer++;
        if ((splitPointer < mixSplits.length) && (splitPointer >= 0)) {
            widgetClearEvents();
            var sPartial = mixSplits[splitPointer];
            preview(sPartial, sPartial + snipWin);
        } else {
            splitPointer--;
        }
    }
});

// select the previous song if present
$("#prev").click(function () {
    if (mixSplits != null && mixSplits != undefined) {
        splitPointer--;
        if ((splitPointer < mixSplits.length) && (splitPointer >= 0)) {
            widgetClearEvents();
            var sPartial = mixSplits[splitPointer];
            preview(sPartial, sPartial + snipWin);
        } else {
            splitPointer++;
        }
    }
});

// sends the waveform of the current track
$("#sendWave").click(function () {
    widget.getSounds(function (sounds) {
        var message = {
            "track": sounds[0],
            "waveform": waveform.data
        };
        sendData(message, "/splitWaveform", setMixSplit);
    });
});

function setMixSplit(newSp) {
    mixSplits = newSp;
    console.log(newSp);
}

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
    mixSplits = null;
    var url = $("url");
    // load the url in the widget
    widget.load(url.val(), {
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

    // empty the input field
    url.val("");
}

// change the volume of the widget
$("#volume").on("input change", function () {
    $widget.setVolume(parseInt($('#volume').val()));
});

// play the snippet of the song
var songStart = parseFloat(start) - (snipWin / 2);
var songEnd = Math.abs(songStart) + snipWin;

//Set the new start time of the preview.
function setStartTime(newStart) {
    start = newStart;
    songStart = parseFloat(start) - (snipWin / 2);
    songEnd = Math.abs(songStart) + snipWin
}

//During the event the current track is seeked to the set songStart and played.
$("#preview").click(function () {
    preview(songStart, songEnd);
});

// Preview a snippet on the current track.
function preview(sStart, sEnd) {
    widgetClearEvents();
    widget.isPaused(function (paused) {
        if (paused) {
            widget.bind(SC.Widget.Events.READY, function () {
                widget.bind(SC.Widget.Events.PLAY, function () {
                    widget.seekTo(sStart);
                    widget.unbind(SC.Widget.Events.PLAY);
                    widget.unbind(SC.Widget.Events.READY);
                });
                widget.play();
            });
        }
    });

    widget.bind(SC.Widget.Events.PLAY_PROGRESS, function () {
        widget.getPosition(function (position) {
            if (position > sEnd) {
                widget.pause();
                widget.unbind(SC.Widget.Events.PLAY_PROGRESS);
            }
        });
    });

    widget.seekTo(sStart);
}

// clear all events on the widget
function widgetClearEvents() {
    widget.unbind(SC.Widget.Events.PLAY);
    widget.unbind(SC.Widget.Events.READY);
    widget.unbind(SC.Widget.Events.PLAY_PROGRESS);
}

// load the widget with a random song
$("#rand").click(function () {
    $.ajax({
        type: "GET",
        dataType: "json",
        contentType: "application/json; charset=utf-8",
        url: "/random",
        success: function (data) {
            widget.load(data.url, {
                auto_play: false,
                likes: false
            });
            setStartTime(data.start)
        }
    });
});

//connect with Soundcloud
$("#connect").click(function () {
    // initiate auth popup
    if (SC.accessToken() == null) {
        SC.connect(function () {
            getFavorites();
        });
        $("#connect").html("Disconnect");
    } else {
        SC.accessToken(null);
        $("#connect").html("Connect");
    }
});

// when pressed the collection of the user is loaded in the widget
$("#favorites").click(function () {
    if (SC.accessToken() != null) {
        getFavorites();
    } else {
        SC.connect(function () {
            getFavorites();
            $("#connect").html("Disconnect");
        });
    }
});

function getFavorites() {
    SC.get('/me', function (me) {
        widget.load("api.soundcloud.com/users/" + me.id + "/favorites", {
            auto_play: false,
            likes: false
        });
    });
}

//initialize client with app credentials
SC.initialize({
    client_id: '70a5f42778b461b7fbae504a5e436c06',
    redirect_uri: 'http://localhost:9000/assets/html/callback.html'
});

// waveform, not in use
$("#waveform").click(function () {
    $widget.bind(SC.Widget.Events.READY, function () {
        // get information about currently playing sound
        $widget.getSounds(function (currentSound) {
            console.log("duration is " + currentSound.duration);
            SC.get("/tracks/" + currentSound[0].id, function (track) {
                var waveform = new Waveform({
                    container: document.getElementById("example"),
                    innerColor: function (x, y) {
                        if (x > songStart / track.duration && x < songEnd / track.duration) {
                            return "#0000FF";
                        }
                        return "#333";
                    }
                });

                waveform.dataFromSoundCloudTrack(track);
                var streamOptions = waveform.optionsForSyncedStream();
                SC.stream(track.uri, streamOptions, function (stream) {
                    window.exampleStream = stream;
                });
            });
        });
    });
});

