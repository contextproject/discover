(function () {
    var widgetIframe = document.getElementById('sc-widget');
    var previewButton = document.querySelector('.preview');
    var widget = SC.Widget(widgetIframe);
    var songStart = parseFloat(previewButton.value);
    var window = 10000;
    var songEnd = songStart + window;
    var url2 = 'http://api.soundcloud.com/users/1539950/favorites';
    //var homeUrl = window.location;

    // Template for adding event listeners.
    function addEvent(element, eventName, callback) {
        if (element.addEventListener) {
            element.addEventListener(eventName, callback, false);
        } else {
            element.attachEvent(eventName, callback, false);
        }
    }

    // Adding the functionality of reloading the wiidget with a given url.
    var reloadButton = document.querySelector('.reload');
    var widgetUrlInput = document.querySelector('.urlInput');
    addEvent(reloadButton, 'click', function () {
        widget.load(widgetUrlInput.value, {
            auto_play: true
        });
    });

    /*
     //Adding the functionality of getTrack button with an id.
     var getButton = document.querySelector('.getTrack');
     var idInput = document.querySelector('.idInput');
     addEvent(getButton, 'click', function() {
     window.location.origin + idInput.value;
     });
     */
    // Adding functionality to the seek button with the given input.
    var seekToButton = document.querySelector('.seekTo');
    var seekInput = seekToButton.querySelector('input');
    addEvent(seekToButton, 'click', function (e) {
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
    addEvent(reloadButton, 'click', function () {
        widget.next();
    });

    // Adding functionality to the 'prev' button.
    var reloadButton = document.querySelector('.prev');
    addEvent(reloadButton, 'click', function () {
        widget.prev();
    });

    // Clear every event on the widget, used for debugging purposses.
    var clearButton = document.querySelector('.clear');
    addEvent(clearButton, 'click', function () {
        widget.unbind(SC.Widget.Events.PLAY);
        widget.unbind(SC.Widget.Events.READY);
    });

    // Adding functionality to the Preview button. Seeks to a certain point in
    // the music and stops after a couple of seconds.
    addEvent(previewButton, 'click', function (e) {
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
                    console.log(songStart)
                    console.log(songEnd)
                    widget.unbind(SC.Widget.Events.PLAY_PROGRESS);
                } // else if ()
            });
        });
    });

    /*
     * var randomSnippet = document.querySelector('.randSnip');
     * addEvent(randomSnippet, 'click', function(e) {
     * widget.bind(SC.Widget.Events.READY, function() { if(widget.isPaused(
     * function(paused) { if( paused) { widget.play(); } } ))
     * widget.bind(SC.Widget.Events.PLAY,function(){ widget.seekTo(randStart);
     * widget.unbind(SC.Widget.Events.PLAY)
     * widget.unbind(SC.Widget.Events.READY) }); });
     *
     * widget.bind(SC.Widget.Events.PLAY_PROGRESS, function() {
     * widget.getPosition( function(position) { if( position > randEnd) {
     * widget.pause(); widget.unbind(SC.Widget.Events.PLAY_PROGRESS) } } ); });
     * });
     */
}());