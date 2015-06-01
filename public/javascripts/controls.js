// Soundcloud widget
var widget = SC.Widget(document.getElementById("sc-widget"));
var mixSplits = [60000, 120000, 180000, 240000];
var snipWin = 5000.00;
var splitPointer = -1;
var waveform;

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

// The method is used to send Data to the server
function sendData(data, url, callback) {
	if (data !== undefined) {
		$.ajax({
			type : "POST",
			dataType : "json",
			data : JSON.stringify(data),
			contentType : "application/json; charset=utf-8",
			url : url,
			success : function(data) {
				console.log(data);
				callback(data.response);
			}
		});
	} else {
		console.log("Object is not usable");
		console.log(data !== undefined);
		console.log(data.length > 0);
	}
}

$("#help").click(function() {
		$('#joyRideTipContent').joyride();
});

//select the next song if present
$("#next").click(function() {
	splitPointer++;
	if((splitPointer < mixSplits.length) && (splitPointer >= 0)) {
		widgetClearEvents();
		var sPartial = mixSplits[splitPointer];
		preview(sPartial, sPartial + snipWin);
	} else {
		splitPointer--;
	}
});

// select the previous song if present
$("#prev").click(function() {
	splitPointer--;
	if((splitPointer < mixSplits.length) && (splitPointer >= 0)) {
		widgetClearEvents();
		var sPartial = mixSplits[splitPointer];
		preview(sPartial, sPartial + snipWin);
	} else {
		splitPointer++;
	}
});

// sends the waveform of the current track
$("#sendWave").click(function() {
	widget.getSounds(function(sounds) {
		var message = {
			"track" : sounds[0],
			"waveform" : waveform.data
		}
		sendData(message, "/splitWaveform", setMixSplit);
	});
	//TO-DO: implement the receiving and setting of multiple start times.
});

function setMixSplit(newSp) {
	console.log(newSp);
}

// if the reload button is clicked on, call the reloadWidget function
$("#reload").click(function() {
	reloadWidget();
});
// if the url submit is entered, call the reloadWidget function
$("#url").keypress(function(e) {
	if (e.which == 13) {
		reloadWidget();
	}
});

// reload the widget with the url submitted in the input field
function reloadWidget() {
	// load the url in the widget
	widget.load($("#url").val(), {
		auto_play : false,
		likes : true
	});
	widget.bind(SC.Widget.Events.READY, function() {
		widget.unbind(SC.Widget.Events.READY);
		var pos;
		widget.getCurrentSoundIndex(function(index) {
			pos = index;
		});
		widget.getSounds(function(sounds) {
			var message = {
				"track" : sounds[pos],
				"waveform" : waveform.data
			}
			sendData(message, "/request", setStartTime);
			// use the following line if you want to re-render the page.
			// window.location.href = "http://localhost:9000/tracks/" + sounds[pos].id;
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
var songStart = parseFloat(start) - (snipWin / 2);
var songEnd = Math.abs(songStart) + snipWin;

//Set the new start time of the preview.
function setStartTime(newStart) {
	start = newStart;
	songStart = parseFloat(start) - (snipWin / 2);
	songEnd = Math.abs(songStart) + snipWin
}

//During the event the current track is seeked to the set songStart and played.
$("#preview").click( function() {
		preview(songStart,songEnd);
});

function preview(sStart, sEnd) {
	widget.bind(SC.Widget.Events.READY, function() {
		if (widget.isPaused(function(paused) {
			if (paused) {
				widget.play();
			}
		}))
		widget.bind(SC.Widget.Events.PLAY, function() {
			widget.seekTo(sStart);
			widget.unbind(SC.Widget.Events.PLAY);
			widget.unbind(SC.Widget.Events.READY);
		});
	});

	widget.bind(SC.Widget.Events.PLAY_PROGRESS, function() {
		widget.getPosition(function(position) {
			if (position > sEnd) {
				widget.pause();
				widget.unbind(SC.Widget.Events.PLAY_PROGRESS);
			}
		});
	});
}

function widgetClearEvents() {
	widget.unbind(SC.Widget.Events.PLAY);
	widget.unbind(SC.Widget.Events.READY);
	widget.unbind(SC.Widget.Events.PLAY_PROGRESS);
}

// load the widget with a random song
$("#rand").click(function() {
	window.location.href = "http://localhost:9000/random";
});

//connect with Soundcloud
$("#connect").click(
	function() {
		// initiate auth popup
		if (SC.accessToken() == null) {
			SC.connect(function() {
				getFavorites();
			});
			$("#connect").html("Disconnect");
		} else {
			SC.accessToken(null);
			$("#connect").html("Connect");
		}
	});

$("#favorites").click(function() {
	if (SC.accessToken() != null) {
		getFavorites();
	} else {
		SC.connect(function() {
			getFavorites();
			$("#connect").html("Disconnect");
		});
	}
});

function getFavorites() {
	SC.get('/me', function(me) {
		widget.load("api.soundcloud.com/users/" + me.id + "/favorites", { 
			auto_play : false 
		});
	});
}

//initialize client with app credentials
SC.initialize({
	client_id : '70a5f42778b461b7fbae504a5e436c06',
	redirect_uri : 'http://localhost:9000/assets/html/callback.html'
});

