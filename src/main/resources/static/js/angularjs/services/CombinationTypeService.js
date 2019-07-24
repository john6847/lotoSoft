/**
 * Created by Dany on 12/05/2019.
 */
'use strict';
angular.module('lottery')
    .factory('CombinationTypeService', ['$http','$q', function ($http, $q) {
    return {
        fetchAllCombinationTypes: fetchAllCombinationTypes,
        fetchAllCombinationTypesSize:fetchAllCombinationTypesSize,
        fetchAllCombinationTypesFiltered: fetchAllCombinationTypesFiltered,
        fetchAllProducts: fetchAllProducts
    };


    function fetchAllCombinationTypes() {
        var deferred = $q.defer();
        $http.get("/api/combinationType/")
            .then(
                function (response) {
                    deferred.resolve(response.data);
                }, function (errResponse) {
                    console.error(errResponse);
                    deferred.reject(errResponse);
                });
        return deferred.promise;
    }

    function fetchAllCombinationTypesFiltered(pageno,itemsPerPage,state) {
        var deferred = $q.defer();
        $http.get("/api/combinationType/find/"+(pageno-1)+"/item/"+itemsPerPage+'/state/'+ state)
            .then(
                function (response) {
                    deferred.resolve(response.data);
                }, function (errResponse) {
                    console.error(errResponse);
                    deferred.reject(errResponse);
                });
        return deferred.promise;
    }

    function fetchAllCombinationTypesSize(state) {
        var deferred = $q.defer();
        $http.get("/api/combinationType/find/size/state/"+state)
            .then(
                function (response) {
                    deferred.resolve(response.data);
                }, function (errResponse) {
                    console.error(errResponse);
                    deferred.reject(errResponse);
                });
        return deferred.promise;
    }

    function fetchAllProducts(state) {
        var deferred = $q.defer();
        $http.get("/api/combinationType/find/products")
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
