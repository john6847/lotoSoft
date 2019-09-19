/**
 * Created by Dany on 12/05/2019.
 */
'use strict';
angular.module('lottery')
    .factory('CombinationService', ['$http', '$q', function ($http, $q) {
        return {
            fetchAllTop3SoldCombination: fetchAllTop3SoldCombination,
            fetchAllBlockedCombination: fetchAllBlockedCombination
        };


        function fetchAllTop3SoldCombination() {
            var deferred = $q.defer();

            $http.get("/api/combination/top/sold")
                .then(
                    function (response) {
                        deferred.resolve(response.data);
                    }, function (errResponse) {
                        console.error(errResponse);
                        deferred.reject(errResponse);
                    });
            return deferred.promise;
        }
        function fetchAllBlockedCombination() {
            var deferred = $q.defer();

            $http.get("/api/combination/blocked")
                .then(
                    function (response) {
                        deferred.resolve(response.data);
                    }, function (errResponse) {
                        console.error(errResponse);
                        deferred.reject(errResponse);
                    });
            return deferred.promise;
        }

    }]);
