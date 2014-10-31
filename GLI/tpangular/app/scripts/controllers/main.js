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
		$http.get('/rest/rides/').success(function (data) {
			$scope.rides = data;
		});
	}
]);

controllers.controller('CreateCtrl', ['$scope', '$state', '$http', 'UserService',
	function ($scope, $state, $http, UserService) {
		$scope.pageName = 'Create a Ride';
		$scope.ride = {
			origin: '',
			destination: '',
			leavingDate: '',
			seatNumber: '',
			driver: null
		};

		$scope.users = [];

		UserService.getAll().then(function (data) {
			$scope.users = data;
		});

		$scope.submit = function () {
			// Deserialize the driver
			$scope.ride.driver = angular.fromJson($scope.ride.driver);

			$http.post('/rest/rides/create/', $scope.ride)
			.success(function (){
				$state.go('home');
			});
		};

		$scope.cancel = function () {
			$state.go('home');
		};
	}
]);

controllers.controller('UpdateCtrl', ['$state', '$stateParams', '$scope', '$http', 'UserService',
	function ($state, $stateParams, $scope, $http, UserService) {
		var id = $stateParams.id;

		$scope.pageName = 'Update';

		UserService.getAll().then(function (data) {
			$scope.users = data;
		});

		$http.get('/rest/rides/' + id).success(function (data) {
			$scope.ride = data;
		});

		$scope.submit = function () {
			$http.put('/rest/rides/update/' + id, $scope.ride)
			.success(function () {
				$state.go('home');
			});
		};

		$scope.cancel = function () {
			$state.go('home');
		};
	}
]);

controllers.controller('DeleteCtrl', ['$state', '$stateParams', '$scope', '$http',
	function ($state, $stateParams, $scope, $http) {
		$http.delete('/rest/rides/delete/' + $stateParams.id)
		.success(function() {
			$state.go('home');
		})
		.error(function (data, status) {
			console.log('Error: ', status);
			$state.go('home');
		});
	}
]);

controllers.controller('RegisterCtrl', ['$state', '$scope', '$http',
	function ($state, $scope, $http) {
		$scope.pageName = 'Create a user';
		$scope.user = {
			username: ''
		};

		$scope.submit = function () {
			$http.post('/rest/users/create/', $scope.user)
			.success(function () {
				$state.go('users.list');
			});
		};

		$scope.cancel = function () {
			$state.go('home');
		};
	}
]);

controllers.controller('UsersCtrl', ['$scope', 'UserService',
	function ($scope, UserService) {
		// TODO
	}
]);

controllers.controller('UserInfoCtrl', ['$scope', 'UserService',
	function ($scope, UserService) {
		// TODO: get user info and save it into the $scope
	}
]);
