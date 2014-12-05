'use strict';

var services = angular.module('tpangularApp');

services.factory('RideService', ['$http', '$q',
	function ($http, $q) {
		var RideService = {};

		RideService.getAll = function () {
			var deferred = $q.defer();

			$http.get('/rest/rides').success(function (data) {
				deferred.resolve(data);
			}).error(function () {
				deferred.reject();
			});

			return deferred.promise;
		};

		RideService.get = function (id) {
			var deferred = $q.defer();

			$http.get('/rest/rides/' + id).success(function (data) {
				deferred.resolve(data);
			}).error(function () {
				deferred.reject();
			});

			return deferred.promise;
		};

		RideService.update = function (ride) {
			var deferred = $q.defer();

			$http.put('/rest/rides/update/' + ride.id, ride).success(function (data) {
				deferred.resolve(data);
			}).error(function () {
				deferred.reject();
			});

			return deferred.promise;
		};

		return RideService;
	}
]);

services.factory('UserService', ['$http', '$q',
	function ($http, $q) {
		var UserService = {};

		UserService.getAll = function () {
			var deferred = $q.defer();

			$http.get('/rest/users').success(function (data) {
				deferred.resolve(data);
			}).error(function () {
				deferred.reject();
			});

			return deferred.promise;
		};

		UserService.get = function (id) {
			var deferred = $q.defer();

			$http.get('/rest/users/' + id).success(function (data) {
				deferred.resolve(data);
			}).error(function () {
				deferred.reject();
			});

			return deferred.promise;
		};

		UserService.update = function (user) {
			var deferred = $q.defer();

			$http.put('/rest/users/update/' + user.id, user).success(function () {
				deferred.resolve();
			}).error(function () {
				deferred.reject();
			});

			return deferred.promise;
		};

		return UserService;
	}
]);
