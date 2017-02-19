function GetArtifactData($scope, $http) {
    $http.get('http://localhost:8080/getartifactdata').
        success(function(data) {
            $scope.artifactdata = data;
        });
}