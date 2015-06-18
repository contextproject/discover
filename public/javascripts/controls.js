// Soundcloud widget
var widget = SC.Widget(document.getElementById("sc-widget"));
var mixSplits, waveform, songStart, songEnd;
var snipWin = 5000.00;
var splitPointer = -1;
setStartTime(parseFloat(start));
var autoplay = false;


widget.bind(SC.Widget.Events.FINISH, function () {
    if (autoplay) {
        widget.getSounds(function (sounds) {
           if (sounds.length > 1){
               widget.next();
           } else{
              recommendAuto();
           }
        });
    }
});

$("#current").click(function () {
    widget.bind(SC.Widget.Events.READY, function () {
        // get information about currently playing sound
        widget.getSounds(function (currentSound) {
            console.log(currentSound[0]);
            var id = currentSound[0].id;
            console.log(id);
        });
    });
});

$('a.toggler.off').click(function () {
    if (document.getElementById("switch").innerHTML == "on") {
        document.getElementById("switch").innerHTML = "off";
        autoplay = false;
    } else {
        document.getElementById("switch").innerHTML = "on";
        autoplay = true;
    }
    $(this).toggleClass('off');

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

$(window).load(function () {
    $('#joyRideTipContent').joyride({
        cookieMonster: true,
        autoStart: true,
        postStepCallback: function (index, tip) {
            if (index == 2) {
                $(this).joyride('set_li', false, 1);
            }
        },
        modal: true,
        expose: true
    });
});

$("#help").click(function () {
    $.removeCookie("joyride", {expires: 365, domain: false, path: false});
    $('#joyRideTipContent').joyride({
        cookieMonster: true,
        preRideCallback: $(this).joyride('destroy', false, 1)
    });

    if (!document.getElementById("switch").innerHTML == "on") {
        widget.bind(SC.Widget.Events.FINISH, function () {
            alert("hoi");
        });
    }
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
    widget.getCurrentSoundIndex(function (index) {
        widget.getSounds(function (sounds) {
            sendData(sounds[index], "/like", function () {
            });
        });
    });
});

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

function setStartTime2(response) {
    snipWin = response.window;
    setStartTime(response.start);
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

function randomSong() {
    $.ajax({
        type: "GET",
        dataType: "json",
        contentType: "application/json; charset=utf-8",
        url: "/random",
        success: function (data) {
            console.log(data);
            widget.load(data.response.url, {
                auto_play: autoplay,
                likes: false
            });
            console.log(data.response);
            setStartTime2(data.response);
        }
    });
}

// load the widget with a random song
$("#rand").click(randomSong);

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
                    SC.get("http://api.soundcloud.com/users/" + data.id + "/favorites", function(favourites) {
                        console.log(favourites);
                        sendData(favourites, "/favorites", function() {

                        });
                    });
                    sendData(data, "/user", function() {
                        console.log("id send");
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
    widget.bind(SC.Widget.Events.READY, function () {
        // get information about currently playing sound
        widget.getSounds(function (currentSound) {
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

function recommendAuto() {
    $.getJSON("/recommend", function (data) {
        var first = data[0];
        reloadWidget("w.soundcloud.com/tracks/" + first["id"]);
    })
}

