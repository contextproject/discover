// initialize client with app credentials
SC.initialize({
	client_id : '70a5f42778b461b7fbae504a5e436c06',
	redirect_uri : 'http://localhost:9000/assets/html/callback.html'
});

//connect with soundcloud and show the users favorites
function connect() {
	SC.connect(function() {
		SC.get('/me', function(me) {
			SC.Widget(widgetIframe).load(
					"api.soundcloud.com/users/" + me.id + "/favorites", {});
		});
	});
};