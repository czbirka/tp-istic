'use strict';

var services = angular.module('tpangularApp');

services.factory('UserService', ['$http', '$q', 
	function ($http, $q) {
		var UserService = {};

		UserService.getAll = function () {
			var deferred = $q.defer();

			$http.get('/rest/drivers').success(function (data) {
				deferred.resolve(data);
			}).error(function () {
				deferred.reject();
			});

			return deferred.promise;
		};

		UserService.get = function (id) {
			// TODO: Get a user with its id
		};

		return UserService;
	}
]);