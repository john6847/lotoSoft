/**
 * Created by Dany on 12/05/2019.
 */
'use strict';
angular.module('lottery')
    .factory('BankService', ['$http', '$q', function ($http, $q) {
        return {
            fetchAllBank: fetchAllBank,
            fetchAllBankFiltered: fetchAllBankFiltered
        };

        function fetchAllBank() {
            var deferred = $q.defer();
            $http.get("/api/bank/")
                .then(
                    function (response) {
                        deferred.resolve(response.data);
                    }, function (errResponse) {
                        console.error(errResponse);
                        deferred.reject(errResponse);
                    });
            return deferred.promise;
        }

        function fetchAllBankFiltered(pageno,itemsPerPage,state) {
            var deferred = $q.defer();

            $http.get("/api/bank/find/"+(pageno-1)+"/item/"+itemsPerPage+'/state/'+ state)
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
