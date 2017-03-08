angular.module('myApp').controller('restCtrl', function($scope, $http, $location, $filter, NgTableParams, $window) {
    
    $http.get("http://127.0.0.1:8080/GetAllArtifacts").then(function (response) {
      $scope.myData = response.data.greetings;
    });
    
    $scope.host = $location.host();
    $scope.port = $location.port();
    
    $scope.tableParams = new NgTableParams({
        sorting: {
            artifactName: 'asc'     
        }
    }, {
        getData: function($defer, params) {
            $scope.myData = $filter('orderBy')($scope.myData, params.orderBy());
            $defer.resolve($scope.myData);
        }
    });
    
    $scope.go = function(x) {
        console.log('http://' + $location.host() + ':' + $location.port() + '/pages/edit.html#/!?artifactName=' + x.artifactName);
        $window.location.href = 'http://' + $location.host() + ':' + $location.port() + '/pages/edit.html#/!?artifactName=' + x.artifactName;
    };
});