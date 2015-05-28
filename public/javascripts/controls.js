// Soundcloud widget
var widget = SC.Widget(document.getElementById("sc-widget"));
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
				callback(data.response)
			}
		});
	} else {
		console.log("Object is not usable");
		console.log(data !== undefined);
		console.log(data.length > 0);
	}
}

// Set the new start time of the preview.
function setStartTime(newStart) {
	start = newStart;
	songStart = parseFloat(start) - (snipWin / 2);
	songEnd = songStart + snipWin
}

// sends the waveform of the current track
$("#sendWave").click(function() {
	sendData(waveform.data, "/splitWaveform", setStartTime)
	//TO-DO: implement the receiving and setting of multiple start times.
});

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
			// window.location.href = "http://localhost:9000/tracks/" +
			// sounds[pos].id;

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

// During the event the current track is seeked to the set songStart and played.
$("#preview").click(function() {
	widget.bind(SC.Widget.Events.READY, function() {
		if (widget.isPaused(function(paused) {
			if (paused) {
				widget.play();
			}
		}))
		widget.bind(SC.Widget.Events.PLAY, function() {
			widget.seekTo(songStart);
			widget.unbind(SC.Widget.Events.PLAY);
			widget.unbind(SC.Widget.Events.READY);
		});
	});

	widget.bind(SC.Widget.Events.PLAY_PROGRESS, function() {
		widget.getPosition(function(position) {
			if (position > songEnd) {
				widget.pause();
				widget.unbind(SC.Widget.Events.PLAY_PROGRESS);
			}
		});
	});
});

// load the widget with a random song
$("#rand").click(function() {
	window.location.href = "http://localhost:9000/random";
});

// select the next song if present
$("#next").click(function() {
	widget.next();
});

// select the previous song if present
$("#prev").click(function() {
	widget.prev();
});

// connect with Soundcloud
// initialize client with app credentials
SC.initialize({
	client_id : '70a5f42778b461b7fbae504a5e436c06',
	redirect_uri : 'http://localhost:9000/assets/html/callback.html'
});

// 
$("#connect").click( function() {
	// initiate auth popup
	if (SC.accessToken() == null) {
		SC.connect(function() {
			SC.get('/me', function(me) {
				widget.load("api.soundcloud.com/users/" + me.id
						+ "/favorites", {});
				$("#connect").html("Disconnect");
			});
		});
	} else {
		SC.accessToken(null);
		$("#connect").html("Connect");
	}
});
