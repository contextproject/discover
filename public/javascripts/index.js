(function() {
	var widgetIframe = document.getElementById('sc-widget');
	var previewButton = document.querySelector('.preview');
	var widget = SC.Widget(widgetIframe);

	// Template for adding event listeners.
	function addEvent(element, eventName, callback) {
		if (element.addEventListener) {
			element.addEventListener(eventName, callback, false);
		} else {
			element.attachEvent(eventName, callback, false);
		}
	}
	
	//Adding the functionality for a volume slider.
	var volSlider = $(".volume"); 
	volSlider.on("input change", function() { 
		widget.setVolume(parseInt(volSlider.val()));
	});
	
	// Adding functionality to the seek button with the given input.
	var seekToButton = document.querySelector('.seekTo');
	var seekInput = seekToButton.querySelector('input');
	addEvent(seekToButton, 'click', function(e) {
		// 'if' prevents the function to trigger when the input field is
		// pressed.
		if (e.target !== this) {
			e.stopPropagation();
			return false;
		}
		widget.seekTo(seekInput.value)
	});

	// Adding functionality to the 'next' button.
	var reloadButton = document.querySelector('.next');
	addEvent(reloadButton, 'click', function() {
		widget.next();
	});

	// Adding functionality to the 'prev' button.
	var reloadButton = document.querySelector('.prev');
	addEvent(reloadButton, 'click', function() {
		widget.prev();
	});

	// Clear every event on the widget, used for debugging purposses.
	var clearButton = document.querySelector('.clear');
	addEvent(clearButton, 'click', function() {
		widget.unbind(SC.Widget.Events.PLAY);
		widget.unbind(SC.Widget.Events.READY);
	});
}());