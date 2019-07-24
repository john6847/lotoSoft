/**
 * Created by Dany on 12/05/2019.
 */
'use strict';
angular.module('lottery')
    .factory('EnterpriseService', ['$http', '$q', function ($http, $q) {
        return {
            fetchAllEnterprise: fetchAllEnterprise,
            fetchAllEnterpriseSize:fetchAllEnterpriseSize,
            fetchAllEnterpriseFiltered: fetchAllEnterpriseFiltered
        };


        function fetchAllEnterprise() {
            var deferred = $q.defer();
            $http.get("/api/enterprise/")
                .then(
                    function (response) {
                        deferred.resolve(response.data);
                    }, function (errResponse) {
                        console.error(errResponse);
                        deferred.reject(errResponse);
                    });
            return deferred.promise;
        }

        function fetchAllEnterpriseFiltered(pageno,itemsPerPage,state) {
            var deferred = $q.defer();

            $http.get("/api/enterprise/find/"+(pageno-1)+"/item/"+itemsPerPage+'/state/'+ state)
                .then(
                    function (response) {
                        deferred.resolve(response.data);
                    }, function (errResponse) {
                        console.error(errResponse);
                        deferred.reject(errResponse);
                    });
            return deferred.promise;
        }

        function fetchAllEnterpriseSize(state) {
            var deferred = $q.defer();
            $http.get("/api/enterprise/find/size/state/"+state)
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
