require.config({
	shim : {
		jquery : {
			exports : "jQuery"
		},
		// knockout has internal checks to make it work with jQuery, see i.e.
		// registerEventHandler
		// therefore jQuery needs to be loaded before.
		knockout : {
			deps : [ 'jquery' ]
		},
		mapping : {
			deps : [ 'knockout' ]
		}
	},

	paths : {
		jquery : 'libs/jquery.min',
		knockout : 'libs/knockout.min',
		mapping : 'libs/knockout.mapping'
	}
});

require([ "knockout", "mapping" ], function(ko, mapping) {

	function ViewModel() {
		// Data
		var self = this;

		self.registerRequest = {
			name : ko.observable()
		};

		self.registerResponse = {
			json : ko.observable()
		};

		self.register = function() {
			$.ajax({
				type : "POST",
				url : "rest/player",
				data : mapping.toJSON(self.registerRequest),
				contentType : 'application/json'
			}).error(function(error) {
				alert("error");
			}).success(function(data) {
				self.registerResponse.json(mapping.toJSON(data));
			});
		};

		self.listPlayerResponse = {
			json : ko.observable()
		};

		self.listPlayer = function() {
			$.ajax({
				type : "GET",
				url : "rest/player"
			}).error(function(error) {
				alert("error");
			}).success(function(data) {
				self.listPlayerResponse.json(mapping.toJSON(data));
			});
		};

		self.createGameRequest = {
			playerid : ko.observable()
		};

		self.createGameResponse = {
			json : ko.observable()
		};

		self.createGame = function() {
			$.ajax({
				type : "POST",
				url : "rest/game",
				data : mapping.toJSON(self.createGameRequest),
				contentType : 'application/json'
			}).error(function(error) {
				alert("error");
			}).success(function(data) {
				self.createGameResponse.json(mapping.toJSON(data));
			});
		};

		self.showGameRequest = {
			gameid : ko.observable()
		};

		self.showGameResponse = {
			json : ko.observable()
		};

		self.showGame = function() {
			$.ajax({
				type : "GET",
				url : "rest/game/" + self.showGameRequest.gameid()
			}).error(function(error) {
				alert("error");
			}).success(function(data) {
				self.showGameResponse.json(mapping.toJSON(data));
			});
		};

		self.joinGameRequest = {
			gameid : ko.observable(),
			playerid : ko.observable()
		};

		self.joinGameResponse = {
			json : ko.observable()
		};

		self.joinGame = function() {
			$.ajax({
				type : "PUT",
				url : "rest/game/" + self.joinGameRequest.gameid(),
				data : mapping.toJSON(self.joinGameRequest),
				contentType : 'application/json'
			}).error(function(error) {
				alert("error");
			}).success(function(data) {
				self.joinGameResponse.json(mapping.toJSON(data));
			});
		};

	}

	var m = new ViewModel();

	ko.applyBindings(m);

});
