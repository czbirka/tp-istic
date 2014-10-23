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
  	$http.get('/rest/rides').success(function (data) {
  		$scope.rides = data;
  	});
});

controllers.controller('CreateCtrl', function ($scope, $state, $http) {
    $scope.pageName = 'Create';
    $scope.ride = {
      origin: '',
      destination: '',
      leavingDate: '',
      seatNumber: ''
   };

   $scope.submit = function () {
      $http.post('/rest/rides/create/', $scope.ride)
      .success(function (){
         $state.go('home');
      });
   };
});


controllers.controller('DeleteCtrl', function ($state, $stateParams, $scope, $http) {
   $http.delete('/rest/rides/delete/' + $stateParams.id)
   .success(function() {
      $state.go('home');
   });
});
