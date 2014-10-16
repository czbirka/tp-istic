'use strict';

/**
 * @ngdoc function
 * @name tpangularApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the tpangularApp
 */
var controllers = angular.module('tpangularApp');

controllers.controller('HomeCtrl', function ($scope, $http) {
  	$http.get('/rest/rides').success(function (data, status, header, config) {
  		$scope.rides = data;
  	});
});
