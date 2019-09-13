/**
 * Created by Dany on 09/05/2019.
 */
app.controller("appController", ['$http', '$scope','$stomp','EnterpriseService','UserService', 'CombinationService', function ($http, $scope, $stomp, EnterpriseService,UserService, CombinationService) {
    $scope.global = {
        systemDate: new Date(),
        combinationsLimited: [],
        users: [],
        topSoldCombinations: []
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
                        $scope.global.combinationsLimited.push({combination: payload.body.combination, type: payload.body.type});
                        $scope.global.combinationsLimited = merge($scope.global.combinationsLimited, [] );
                        $scope.$apply($scope.global.combinationsLimited);
                    });
            }

            if($scope.enterpriseId > 0) {
                $stomp.subscribe('/topics/' + $scope.enterpriseId + '/' + 6 + '/event',
                    function (payload, headers, res) {
                        console.log(payload);
                        $scope.global.users = merge($scope.global.users, payload.body.users);
                        $scope.$apply($scope.global.users);
                    });
            }
        });

    function fetchEnterprise() {
        EnterpriseService.fetchEnterprise()
            .then(
                function (d) {
                    $scope.enterpriseId = d;
                    fetchAllUsersLogged();
                    fetchAllTop3SoldCombination();
                },
                function (errorResponse) {
                    console.error(errorResponse);
                })
    }

    function fetchAllUsersLogged() {
        UserService.fetchAllUsersLogged()
            .then(
                function (d) {
                    $scope.global.users = d;
                    console.log(d)
                },
                function (errorResponse) {
                    console.error(errorResponse);
                });
    }

    function fetchAllTop3SoldCombination() {
        CombinationService.fetchAllTop3SoldCombination()
            .then(
                function (d) {
                    $scope.global.topSoldCombinations = d;
                    console.log(d)
                },
                function (errorResponse) {
                    console.error(errorResponse);
                });
    }

    function merge(old,newArray) {
        var d =[];
        old.concat(newArray).forEach(function (item) {
            if (d.indexOf(item) === -1)
                d.push(item);
        });
        return d;
    }


}]);
