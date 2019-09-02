/**
 * Created by Dany on 09/05/2019.
 */
app.controller("appController", ['$http', '$scope','$stomp', function ($http, $scope, $stomp) {
    $scope.global = {
        systemDate: new Date()
    };
    $stomp.connect('http://localhost:3200/live', {})
        .then(function (frame) {
            $stomp.subscribe('/topics/time',
                function (payload, headers, res) {
                    $scope.global.systemDate = new Date(payload);
                    $scope.$apply($scope.global.systemDate);
                });
        });
}]);