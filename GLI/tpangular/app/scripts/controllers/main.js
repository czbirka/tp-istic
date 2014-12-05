'use strict';

/**
 * @ngdoc function
 * @name tpangularApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the tpangularApp
 */
var controllers = angular.module('tpangularApp');

controllers.controller('HomeCtrl', ['$scope', 'RideService',
	function ($scope, RideService) {
		RideService.getAll().then(function (data) {
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
			leavingDate: new Date(),
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

controllers.controller('ViewCtrl', ['$state', '$stateParams', '$scope', 'RideService', 'UserService', '$q', '$modal',
	function ($state, $stateParams, $scope, RideService, UserService, $q, $modal) {
		$q.all([
			RideService.get($stateParams.id), 
			UserService.getAll()
		]).then(function (data) {
			$scope.ride = data[0];
			$scope.users = data[1];
		});

		var joinModal = $modal({scope: $scope, template: 'views/modal/join_modal.tpl.html', show: false});

		$scope.askUsername = function () {
			joinModal.$promise.then(joinModal.show);
		};

		$scope.userSelected = function (object) {
			return object !== undefined;
		};

		$scope.join = function (user) {	
			var ride = $scope.ride;

			ride.passengers.push(angular.fromJson(user));
			RideService.update(ride);

			joinModal.hide();
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
			// Deserialize the driver
			$scope.ride.driver = angular.fromJson($scope.ride.driver);
			
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
		UserService.getAll().then(function (data) {
			$scope.users = data;
		});
	}
]);

controllers.controller('UserInfoCtrl', ['$scope', '$stateParams', 'UserService',
	function ($scope, $stateParams, UserService) {
		UserService.get($stateParams.id).then(function (data) {
			$scope.user = data;
			$scope.pageName = $scope.user.username;
		});
	}
]);

controllers.controller('UpdateUserCtrl', ['$scope', '$state', '$stateParams', 'UserService', '$alert',
	function ($scope, $state, $stateParams, UserService, $alert) {
		UserService.get($stateParams.id).then(function (data) {
			$scope.user = data;
		});

		$scope.submit = function () {
			UserService.update($scope.user).then(function () {
				$alert({
					title:'Success', 
					content: 'User updated successfully',
					type: 'success',
					show: true
				});
				$state.go('users.list');
			}, function (data) {
				console.log(data);
			});
		};
	}
]);

controllers.controller('DeleteUserCtrl', ['$state', '$stateParams', '$scope', '$http',
	function ($state, $stateParams, $scope, $http) {
		$http.delete('/rest/users/delete/' + $stateParams.id)
		.success(function() {
			$state.go('users.list');
		});
	}
]);
