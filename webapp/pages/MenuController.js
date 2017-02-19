angular.module('myApp').controller('MenuController', function($scope, $http) {

    // Always reload page
    //$route.reload();
    
    $http.get("http://127.0.0.1:8080/GetMessages").then(function (response) {
      $scope.myMessages = response.data.messages;
    });

    $http.get("http://127.0.0.1:8080/GetNotifications").then(function (response) {
      $scope.myNotifications = response.data.messages;
    });

    $http.get("http://127.0.0.1:8080/GetStatistics").then(function (response) {
      $scope.myStatistics = response.data;
    });
    
    $scope.leftMenu = [
        {link:'index.html',style:'fa fa-dashboard fa-fw', text:'Dashboard'},
        {link:'artifacts.html',style:'fa fa-table fa-fw', text:'Configuration Artifacts'},
        {link:'audit.html',style:'fa fa-table fa-fw', text:'Audit'},
        {link:'backup.html',style:'fa fa-wrench fa-fw', text:'Backup'},
        {link:'environments.html',style:'fa fa-sitemap fa-fw', text:'Environments'},
        {link:'validation.html',style:'fa fa-edit fa-fw', text:'Configuration Validation'},
        {link:'sync.html',style:'fa fa-edit fa-fw', text:'Sync Configuration'},
        {link:'system.html',style:'fa fa-wrench fa-fw', text:'System'}
    ];
});