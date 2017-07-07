mainApp.controller('homeController', function($scope, $route, filmsApiService) {
	
	$scope.films = [];
	$scope.selected = {};
	$scope.deleteError = "";
	$scope.addError = "";
	$scope.added = {
		title: "",
		year: ""
	};
	
	filmsApiService.getFilms().then(
		function(data) {
			$scope.films = data;
		},
		function(error) {
			console.log("Failed to get the list of films: " + error);
		}
	);
	
	$scope.doSelectFilm = function(film) {
		// clear previous error messages
		$scope.deleteError = "";
		$scope.addError = "";

		$scope.selected = film;
	}
	
	$scope.doDelete = function() {
		// clear previous error messages
		$scope.deleteError = "";
		$scope.addError = "";
		
		// Modify this function so that it shows a modal confirmation dialog
		// and only deletes the selected film if you click Yes.
		filmsApiService.deleteFilm($scope.selected.id).then(
			function(data) {
				// Film was deleted which means film list has changed
				// so reload the web page.
				$route.reload();
			},
			function(error) {
				$scope.deleteError = "Failed to delete film with id: " + $scope.selected.id + ". " + error;
			}
		);
	}
	
	$scope.doAdd = function() {
		// clear previous error messages
		$scope.deleteError = "";
		$scope.addError = "";
		
		filmsApiService.postFilm($scope.added).then(
			function(data) {
				// Film was added which means film list has changed
				// so reload the web page.
				$route.reload();
			},
			function(error) {
				$scope.addError = "Failed to add film. " + error;
			}
		);
	}
	
});
