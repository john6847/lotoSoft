/**
 * Created by Dany on 12/05/2019.
 */
'use strict';
angular.module('lottery')
    .factory('PosService', ['$http', '$q', function ($http, $q) {
        return {
            fetchAllPos: fetchAllPos,
            fetchAllPosSize:fetchAllPosSize,
            fetchAllPosFiltered: fetchAllPosFiltered,
            fetchPosBySeller : fetchPosBySeller
        };


        function fetchAllPos() {
            var deferred = $q.defer();
            $http.get("/api/pos/")
                .then(
                    function (response) {
                        deferred.resolve(response.data);
                    }, function (errResponse) {
                        console.error(errResponse);
                        deferred.reject(errResponse);
                    });
            return deferred.promise;
        }

        function fetchAllPosFiltered(pageno,itemsPerPage,state) {
            var deferred = $q.defer();

            $http.get("/api/pos/find/"+(pageno-1)+"/item/"+itemsPerPage+'/state/'+ state)
                .then(
                    function (response) {
                        deferred.resolve(response.data);
                    }, function (errResponse) {
                        console.error(errResponse);
                        deferred.reject(errResponse);
                    });
            return deferred.promise;
        }

        function fetchAllPosSize(state) {
            var deferred = $q.defer();
            $http.get("/api/pos/find/size/state/"+state)
                .then(
                    function (response) {
                        deferred.resolve(response.data);
                    }, function (errResponse) {
                        console.error(errResponse);
                        deferred.reject(errResponse);
                    });
            return deferred.promise;
        }

        function fetchPosBySeller(id) {
            var deferred = $q.defer();
            $http.get("/api/pos/find/seller/"+ id)
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
