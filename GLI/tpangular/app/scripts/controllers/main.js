'use strict';

/**
 * @ngdoc function
 * @name tpangularApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the tpangularApp
 */
var controllers = angular.module('tpangularApp');

controllers.controller('HomeCtrl', ['$scope', '$http', 
	function ($scope, $http) {
		$http.get('/rest/rides').success(function (data) {
			$scope.rides = data;
		});
	}
]);

controllers.controller('CreateCtrl', ['$scope', '$state', '$http', 
	function ($scope, $state, $http) {
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
	}
]);

controllers.controller('UpdateCtrl', ['$state', '$stateParams', '$scope', '$http',
	function ($state, $stateParams, $scope, $http) {
		var id = $stateParams.id;

		$scope.pageName = 'Update';

		$http.get('/rest/rides/search/' + id)
		.success(function (data) {
			$scope.ride = data;
		});

		$scope.submit = function () {
			$http.put('/rest/rides/update/' + id, $scope.ride)
			.success(function () {
				$state.go('home');
			});
		};
	}
]);

controllers.controller('DeleteCtrl', ['$state', '$stateParams', '$scope', '$http', 
	function ($state, $stateParams, $scope, $http) {
		$http.delete('/rest/rides/delete/' + $stateParams.id)
		.success(function() {
			$state.go('home');
		});
	}
]);
