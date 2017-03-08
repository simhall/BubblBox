var app = angular.module('myApp', ['ngTable']);

app.config(['$locationProvider', function AppConfig($locationProvider) {
    $locationProvider.html5Mode(true);
    $locationProvider.hashPrefix('!');

}]);