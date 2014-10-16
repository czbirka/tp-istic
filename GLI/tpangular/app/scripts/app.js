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
                    'ngCookies',
                    'ngResource',
                    'ngRoute',
                    'ngSanitize',
                    'ui.router'
                  ]);

angularApp.config(function ($stateProvider, $urlRouterProvider) {
    $urlRouterProvider.otherwise('/home');

    $stateProvider.state('home', {
      url: '/home',
      templateUrl: 'views/home.tpl.html',
      controller: 'HomeCtrl'
    });
  });
