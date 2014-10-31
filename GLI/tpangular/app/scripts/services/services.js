'use strict';

var services = angular.module('tpangularApp');

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

		return UserService;
	}
]);
