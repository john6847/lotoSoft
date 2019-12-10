/**
 * Created by Dany on 09/05/2019.
 */
app.controller("notificationController", ['$http', '$scope','$stomp','EnterpriseService', 'NotificationService', function ($http, $scope, $stomp, EnterpriseService, NotificationService) {
    $scope.global = {
        notifications: []
    };

    $scope.enterpriseId = 0;
    fetchEnterprise();
    $stomp.connect('http://lotosof.com:3200/live', {})
        .then(function (frame) {
            if($scope.enterpriseId > 0){
                $stomp.subscribe('/topics/'+$scope.enterpriseId+'/'+5+'/event',
                    function (payload, headers, res) {
                        $scope.global.users = merge($scope.global.notifications, payload.body.notifications);
                        $scope.$apply($scope.global.notifications);
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
                    fetchAllNotifications();
                },
                function (errorResponse) {
                    console.error(errorResponse);
                })
    }

    function fetchAllNotifications() {
        NotificationService.fetchAllNotifications()
            .then(
                function (d) {
                    $scope.global.notifications = d;
                },
                function (errorResponse) {
                    console.error(errorResponse);
                });
    }

}]);
