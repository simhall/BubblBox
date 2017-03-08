angular.module('myApp').controller('backupArtifactController', function($scope, $http, $location, $filter, NgTableParams, $window) {
    
    $http.get("http://127.0.0.1:8080/GetAllPreviousArtifacts").then(function (response) {
      $scope.previousArtifacts = response.data.previousVersions;
    });
    
    $scope.host = $location.host();
    $scope.port = $location.port();
    
    $scope.tableParams = new NgTableParams({
        sorting: {
            artifactName: 'asc'     
        }
    }, {
        getData: function($defer, params) {
            $scope.previousArtifacts = $filter('orderBy')($scope.previousArtifacts, params.orderBy());
            $defer.resolve($scope.previousArtifacts);
        }
    });
    
    $scope.go = function(x) {
        console.log('http://' + $location.host() + ':' + $location.port() + '/pages/edit.html#/!?artifactName=' + x.artifactName);
        $window.location.href = 'http://' + $location.host() + ':' + $location.port() + '/pages/edit.html#/!?artifactName=' + x.artifactName;
    };
});