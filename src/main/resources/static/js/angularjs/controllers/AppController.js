/**
 * Created by Dany on 09/05/2019.
 */
app.controller("appController", ['$http', '$scope','$stomp','EnterpriseService','UserService' ,function ($http, $scope, $stomp, EnterpriseService,UserService) {
    $scope.global = {
        systemDate: new Date(),
        combinationsLimited: [],
        connectedUsers: []
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
                $stomp.subscribe('/topics/'+$scope.enterpriseId+'/'+5+'/event',
                    function (payload, headers, res) {
                        $scope.global.combinationsLimited.push(payload);
                        $scope.$apply($scope.global.combinationsLimited);
                    });
            }

            if($scope.enterpriseId > 0) {
                $stomp.subscribe('/topics/' + $scope.enterpriseId + '/' + 6 + '/event',
                    function (payload, headers, res) {
                        console.log(payload);
                        $scope.connectedUsers = merge($scope.connectedUsers, payload);
                        $scope.$apply($scope.global.connectedUsers);
                    });
            }
        });

    function fetchEnterprise() {
        EnterpriseService.fetchEnterprise()
            .then(
                function (d) {
                    $scope.enterpriseId = d;
                    fetchAllConnectedUser();
                },
                function (errorResponse) {
                    console.error(errorResponse);
                })
    }

    function fetchAllConnectedUser() {
        UserService.fetchAllConnectedUser()
            .then(
                function (d) {
                    $scope.global.connectedUsers = d;
                },
                function (errorResponse) {
                    console.error(errorResponse);
                })
    }

    function merge(old,newArray) {
        var d =[];
        x.concat(old, newArray).forEach(function (item) {
            if (d.indexOf(item) === -1)
                d.push(item);
        });
        return d;
    }


}]);
