angular.module('myApp').controller('EditArtifactController', function($scope, $http, $location) {    
    $scope.artifactName = $location.search()['artifactName'];
  
    $http.get("http://127.0.0.1:8080/GetArtifactData?artifactName=" + $scope.artifactName).then(function (response) {
      $scope.artifactData = response.data;
    });
    
    $scope.sendPost = function() {
        var data = $.param({
            json: JSON.stringify({
                name: $scope.artifactData
            })
        });
        
        $http.post("http://127.0.0.1:8080/SetArtifactData", $scope.artifactData).success(function(data, status) {
            $scope.hello = $scope.artifactData;
        })
    }
    
    
});