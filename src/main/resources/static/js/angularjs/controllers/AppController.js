/**
 * Created by Dany on 09/05/2019.
 */
app.controller("appController", ['$http', '$scope','$stomp','EnterpriseService' ,function ($http, $scope, $stomp, EnterpriseService) {
    $scope.global = {
        systemDate: new Date(),
        combinationsLimited: []

    };
    $scope.enterpriseId = 0;
    fetchEnterprise();
    $stomp.connect('http://localhost:3200/live', {})
        .then(function (frame) {
            $stomp.subscribe('/topics/time',
                function (payload, headers, res) {
                    $scope.global.systemDate = new Date(payload);
                    $scope.$apply($scope.global.systemDate);
                });
            if($scope.enterpriseId > 0){
                $stomp.subscribe('/topics/'+$scope.enterpriseId+'/'+5+'/time',
                    function (payload, headers, res) {
                        $scope.global.combinationsLimited.push(payload);
                        $scope.$apply($scope.global.combinationsLimited);
                    });
            }
        });

    function fetchEnterprise() {
        EnterpriseService.fetchEnterprise()
            .then(
                function (d) {
                    $scope.enterpriseId = d;
                },
                function (errorResponse) {
                    console.error(errorResponse);
                })
    }
}]);