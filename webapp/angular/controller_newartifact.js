angular.module('myApp').controller('NewArtifactController', function($scope, $http, $location, $log) {    
    
    $scope.artifactData = { };
    $scope.artifactData.artifactName = "";
    
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

    $scope.uploadFile = function() {
        $log.log("got file");
        var f = document.getElementById('file').files[0],
            r = new FileReader();
        r.onloadend = function(e){
            var data = e.target.result;
            //send your binary data via $http or $resource or do anything else with it
        }
        r.readAsBinaryString(f);
        $log.log("file name: " + f.name);
        $scope.artifactData.artifactName = f.name;
        $scope.artifactData.artifactName = f.name;
        
    }
});