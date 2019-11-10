/**
 * Created by Dany on 09/05/2019.
 */
app.controller("notificationController", ['$http', '$scope','$stomp','EnterpriseService', function ($http, $scope, $stomp, EnterpriseService) {
    $scope.global = {
        notifications: [{example: "231"}],
    };

    $scope.enterpriseId = 0;
    fetchEnterprise();
    $stomp.connect('https://lotosof.com/live', {})
        .then(function (frame) {
            if($scope.enterpriseId > 0){
                $stomp.subscribe('/topics/'+$scope.enterpriseId+'/'+8+'/event',
                    function (payload, headers, res) {
                        $scope.global.notifcations.push({combination: payload.body.notifications, type: payload.body.type});
                        $scope.global.notifcations = merge($scope.global.notifcations, []);
                        $scope.$apply($scope.global.notifcations);
                    });
            }

        });

    function merge(old,newArray) {
        var d =[];
        old.concat(newArray).forEach(function (item) {
            if (d.indexOf(item) === -1)
                d.push(item);
        });
        return d;
    }


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

    // function fetchAllNotifications() {
    //     UserService.fetchAllUsersLogged()
    //         .then(
    //             function (d) {
    //                 $scope.global.users = d;
    //             },
    //             function (errorResponse) {
    //                 console.error(errorResponse);
    //             });
    // }

}]);
