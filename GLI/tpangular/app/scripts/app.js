'use strict';

/**
 * @ngdoc overview
 * @name tpangularApp
 * @description
 * # tpangularApp
 *
 * Main module of the application.
 */
var angularApp = angular.module('tpangularApp', [
	'ngCookies',
	'ngResource',
	'ngRoute',
	'ngSanitize',
	'ui.router',
]);

angularApp.config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {
	$urlRouterProvider.otherwise('/home');

	$stateProvider.state('home', {
		url: '/home',
		templateUrl: 'views/home.tpl.html',
		controller: 'HomeCtrl'
	})
	.state('create', {
		url: '/create',
		templateUrl: 'views/rideForm.tpl.html',
		controller: 'CreateCtrl'
	})
	.state('update', {
		url: '/update/:id',
		templateUrl: 'views/rideForm.tpl.html',
		controller: 'UpdateCtrl'
	})
	.state('delete', {
		url: '/delete/:id',
		controller: 'DeleteCtrl'
	})
	.state('register', {
		url: '/register',
		templateUrl: 'views/userForm.tpl.html',
		controller: 'RegisterCtrl'
	})
	.state('user', {
		url: '/user/:id',
		templateUrl: 'views/userInfo.tpl.html',
		controller: 'UserInfoCtrl'
	});
}]);
