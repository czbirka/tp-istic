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

controllers.controller('CreateCtrl', ['$scope', '$state', 'RideService', 'UserService', '$alert',
	function ($scope, $state, RideService, UserService, $alert) {
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

			RideService.create($scope.ride).then(function () {
				$alert({
					title:'Success:', 
					content: 'The ride was created!',
					type: 'success',
					show: true
				});
				$state.go('home');
			}, function () {
				$alert({
					title:'Error',
					content: 'Failed to create the ride.',
					type: 'danger',
					show: true
				});
				$state.go('home');
			});
		};

		$scope.cancel = function () {
			$state.go('home');
		};
	}
]);

controllers.controller('ViewCtrl', ['$state', '$stateParams', '$scope', 'RideService', 'UserService', '$q', '$modal', '$alert',
	function ($state, $stateParams, $scope, RideService, UserService, $q, $modal, $alert) {
		$q.all([
			RideService.get($stateParams.id), 
			UserService.getAll()
		]).then(function (data) {
			// Ride informations
			$scope.ride = data[0];
			$scope.users = [];

			var driver = $scope.ride.driver;
			var passengers = $scope.ride.passengers;

			// Remove the driver from the user list
			var users = data[1].filter(function (element) {
				return element.id !== driver.id;
			});

			// 
			angular.forEach(users, function (user) {
				if (user.ridesAsPassengerID.indexOf($scope.ride.id) == -1) {
					$scope.users.push(user);
				}
			});
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
			user = angular.fromJson(user);

			RideService.addPassengerToRide(user, ride).then(function () {
				$alert({
					title:'Success:', 
					content: 'You have joined the ride!',
					type: 'success',
					show: true
				});
				$scope.redirect();
			}, function () {
				$alert({
					title:'Error:',
					content: 'Failed to join the ride.',
					type: 'danger',
					show: true
				});
				$scope.redirect();
			});
		};

		$scope.redirect = function () {
			joinModal.hide();
			$state.go($state.current, $stateParams, {reload: true});
		};
	}
]);

controllers.controller('UpdateCtrl', ['$state', '$stateParams', '$scope', '$http', 'UserService', '$alert',
	function ($state, $stateParams, $scope, $http, UserService, $alert) {
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
				$alert({
					title:'Success:', 
					content: 'The ride has been updated.',
					type: 'success',
					show: true
				});
				$state.go('home', $stateParams, {reload: true});
			});
		};

		$scope.cancel = function () {
			$state.go('home');
		};
	}
]);

controllers.controller('DeleteCtrl', ['$state', '$stateParams', '$scope', '$http', '$alert',
	function ($state, $stateParams, $scope, $http, $alert) {
		$http.delete('/rest/rides/delete/' + $stateParams.id)
		.success(function() {
			$alert({
				title:'Success:', 
				content: 'The ride was deleted.',
				type: 'success',
				show: true
			});
			$state.go('home');
		})
		.error(function (data, status) {
			$alert({
				title:'Error:', 
				content: status,
				type: 'danger',
				show: true
			});
			$state.go('home');
		});
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
		});
	}
]);

controllers.controller('RegisterCtrl', ['$state', '$scope', '$http', '$alert',
	function ($state, $scope, $http, $alert) {
		$scope.pageName = 'Create a user';
		$scope.user = {
			username: ''
		};

		$scope.submit = function () {
			$http.post('/rest/users/create/', $scope.user)
			.success(function () {
				$alert({
					title:'Success:', 
					content: 'The user has been created successfully.',
					type: 'success',
					show: true
				});
				$state.go('users.list');
			});
		};

		$scope.cancel = function () {
			$state.go('home');
		};
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
					title:'Success:', 
					content: 'The user has been updated.',
					type: 'success',
					show: true
				});
				$state.go('users.list');
			}, function (data) {
				console.log(data);
			});
		};

		$scope.cancel = function () {
			$state.go('home');
		};
	}
]);

controllers.controller('DeleteUserCtrl', ['$state', '$stateParams', '$scope', '$http', '$alert',
	function ($state, $stateParams, $scope, $http, $alert) {
		$http.delete('/rest/users/delete/' + $stateParams.id)
		.success(function() {
			$alert({
				title:'Success:', 
				content: 'The user was deleted.',
				type: 'success',
				show: true
			});
			$state.go('users.list');
		});
	}
]);
