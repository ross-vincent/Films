var mainApp = angular.module("mainApp", ['ngRoute']);

mainApp.config(function($routeProvider) {
	$routeProvider
		.when('/home', {
			templateUrl: 'home/home.html',
			controller: 'homeController'
		})
		.otherwise({
			redirectTo: '/home'
		});
});
