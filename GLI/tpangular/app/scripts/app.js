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
	'ngAnimate',
	'ngSanitize',
	'ui.router',
	'mgcrea.ngStrap'
]);

angularApp.config(['$stateProvider', '$urlRouterProvider', '$alertProvider',
	function ($stateProvider, $urlRouterProvider, $alertProvider) {
		angular.extend($alertProvider.defaults, {
			duration: 3,
			container: 'body',
			placement: 'top-right'
		});

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
		.state('rides.view', {
			url: '/view/:id',
			templateUrl: 'views/rideView.tpl.html',
			controller: 'ViewCtrl'
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
			templateUrl: 'views/userForm.tpl.html',
			controller: 'UpdateUserCtrl'
		})
		.state('users.delete', {
			url: '/delete/:id',
			controller: 'DeleteUserCtrl'
		});
	}
]);
