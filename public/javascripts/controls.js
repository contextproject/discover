// Soundcloud widget
var widget = SC.Widget(document.getElementById("sc-widget"));
var mixSplits, waveform, songStart, songEnd;
var snipWin = 5000.00;
var splitPointer = -1;
setStartTime(parseFloat(start));

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
widget.bind(SC.Widget.Events.READY, function () {
    waveform = new Waveform({
        container: document.getElementById("sc-widget")
    });
    widget.getSounds(function (sounds) {
        waveform.dataFromSoundCloudTrack(sounds[0]);
    });
    widget.unbind(SC.Widget.Events.READY);
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
                sendData(sounds[index], "/dislike", function () {
                });
                SC.delete('/me/favorites/' + sounds[index].id);
            });
        });
    }
});

// event for liking a song
$("#like").click(function () {
    like();
    if (SC.accessToken() != null) {
        like();
    }
});

// like the current song
function like() {
    widget.getCurrentSoundIndex(function (index) {
        widget.getSounds(function (sounds) {
            sendData(sounds[index], "/like", function () {
            });
            //SC.put('/me/favorites/' + sounds[index].id);
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
    clearInputAndReloadWidget();
});
// if the url submit is entered, call the reloadWidget function
$("#url").keypress(function (e) {
    if (e.which == 13) {
        clearInputAndReloadWidget();
    }
});

function clearInputAndReloadWidget() {
    var url = $("#url");
    var input = url.val();
    url.html("");
    reloadWidget(input);
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
            // use the following line if you want to re-render the page.
            // window.location.href = "http://localhost:9000/tracks/" + sounds[pos].id;
        });
    });
}

// change the volume of the widget
$("#volume").on("input change", function () {
    widget.setVolume(parseInt($('#volume').val()));
});


//change the mode of the algorithm
$("#algoMode").on("input change", function () {
	var val = $("#algoMode").val();
	if (val <= 25) {
		$("#modeLabel").text("AUTO");
		sendData({ "mode": "auto" }, "/setPreviewMode", function () {});
	} else if (val > 25 & val <= 50) {
		$("#modeLabel").text("INTENSITY");
		sendData({ "mode": "intensity" }, "/setPreviewMode", function () {});
	} else if (val > 50 & val <= 75) { 
		$("#modeLabel").text("CONTENT");
		sendData({ "mode": "content" }, "/setPreviewMode", function () {});
	} else {
		$("#modeLabel").text("RANDOM");
		sendData({ "mode": "random" }, "/setPreviewMode", function () {});
	}
});

//Set the new start time of the preview.
function setStartTime(newStart) {
	if(newStart < 0 || newStart < (snipWin / 2)) {
		songStart = 0;
		songEnd = snipWin;
	} else {
	    songStart = parseFloat(newStart) - (snipWin / 2);
	    songEnd = Math.abs(songStart) + snipWin
	}
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
            setStartTime(data.start);
        }
    });
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

//connect with Soundcloud
$("#connect").on('click', function () {
    if ($(this).attr("class") == "connect") {
        if (SC.accessToken() == null) {
            SC.connect(function () {
                getFavorites2(function (data) {
                    console.log(data);
                    sendData(data, "/favourites", function() {
                        console.log("succes");
                    });
                    loadFavorites(data);
                });
            });
            $(this).html("Disconnect");
            $(this).attr("class", "disconnect");
        }
    } else {
        if (SC.accessToken() != null) {
            SC.accessToken(null);
            $(this).html("Connect");
            $(this).attr("class", "connect");
        }
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

function getFavorites2(callback) {
    SC.get('/me', function (me) {
        callback(me);
    });
}

function loadFavorites(me) {
    widget.load("api.soundcloud.com/users/" + me.id + "/favorites", {
        auto_play: false,
        likes: false
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

