/**
 * Created by Dany on 09/05/2019.
 */
app.controller("appController", ['$http', '$scope','$stomp','Constants','EnterpriseService','UserService', 'CombinationService', function ($http, $scope, $stomp, Constants, EnterpriseService,UserService, CombinationService) {
    $scope.global = {
        systemDate: new Date(),
        combinationsLimited: [],
        users: [],
        topSoldCombinations: [],
        blockedCombinations: [],
        constant: Constants.Products
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

                $stomp.subscribe('/topics/' + $scope.enterpriseId + '/' + 6 + '/event',
                    function (payload, headers, res) {
                        $scope.global.users = merge($scope.global.users, payload.body.users);
                        $scope.$apply($scope.global.users);
                    });

                $stomp.subscribe('/topics/' + $scope.enterpriseId + '/' + 7 + '/event',
                    function (payload, headers, res) {
                        $scope.global.topSoldCombinations = groupArrayByCombinationType(payload.body.combinations);
                        $scope.$apply($scope.global.topSoldCombinations);
                        console.log($scope.global.topSoldCombinations)
                    });

                $stomp.subscribe('/topics/' + $scope.enterpriseId + '/' + 0 + '/event',
                    function (payload, headers, res) {
                        $scope.global.blockedCombinations = payload.body.combination;
                        $scope.$apply($scope.global.blockedCombinations);
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
                    fetchAllBlockedCombination();
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
                },
                function (errorResponse) {
                    console.error(errorResponse);
                });
    }

    function fetchAllTop3SoldCombination() {
        CombinationService.fetchAllTop3SoldCombination()
            .then(
                function (d) {
                    $scope.global.topSoldCombinations = groupArrayByCombinationType(d);
                },
                function (errorResponse) {
                    console.error(errorResponse);
                });
    }

    function fetchAllBlockedCombination() {
        CombinationService.fetchAllBlockedCombination()
            .then(
                function (d) {
                    $scope.global.blockedCombinations = d;
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

    function groupArrayByCombinationType(array) {
        $scope.global.topSoldCombinations = [];
        var arrReduce = [];
        if (array && array.length > 0){
            var data = readElements(array);
            if (data && data.length > 0){
                arrReduce = data.reduce(function(results, org) {
                    (results[org.combination_type_description] = results[org.combination_type_description] || []).push(org);
                    return results;
                }, {});
            }
        }
        return arrReduce;
    }

    function readElements(array) {
        var resultArr = [];
        for (var i = 0; i< array.length; i++) {
            resultArr.push(convertArrayToObject(array[i]));
        }
        return resultArr;
    }

    function convertArrayToObject(array) {
        var obj = {};
        obj["r"] = array[0];
        obj["id"] = array[1];
        obj["enabled"] = array[2];
        obj["max_price"] = array[3];
        obj["result_combination"] = array[4];
        obj["sale_total"] = array[5];
        obj["sequence"] = array[6];
        obj["combination_type_id"] = array[7];
        obj["combination_type_description"] = $scope.global.constant.find(function (constant) {
            return constant.Id === array[7];
        }).Name;
        obj["enterprise_id"] = array[8];
        obj["number_three_digits_id"] = array[9];

        return obj;
    }


}]);
