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
	.state('rides', {
		abstract: true,
		url: '/rides',
		template: '<ui-view/>'
	})
	.state('rides.create', {
		url: '/create',
		templateUrl: 'views/rideForm.tpl.html',
		controller: 'CreateCtrl'
	})
	.state('rides.update', {
		url: '/update/:id',
		templateUrl: 'views/rideForm.tpl.html',
		controller: 'UpdateCtrl'
	})
	.state('rides.delete', {
		url: '/delete/:id',
		controller: 'DeleteCtrl'
	})
	.state('users', {
		abstract: true,
		url: '/users',
		template: '<ui-view/>'
	})
	.state('users.list', {
		url: '/',
		templateUrl: 'views/userList.tpl.html',
		controller: 'UsersCtrl'
	})
	.state('users.register', {
		url: '/register',
		templateUrl: 'views/userForm.tpl.html',
		controller: 'RegisterCtrl'
	})
	.state('users.user', {
		url: '/:id',
		templateUrl: 'views/userInfo.tpl.html',
		controller: 'UserInfoCtrl'
	})
	.state('users.update', {
		url: '/update/:id',
		templateUrl: 'views/userInfo.tpl.html',
		controller: 'UpdateUserCtrl'
	})
	.state('users.delete', {
		url: '/delete/:id',
		controller: 'DeleteUserCtrl'
	});
}]);
