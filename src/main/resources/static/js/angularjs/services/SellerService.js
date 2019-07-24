/**
 * Created by Dany on 12/05/2019.
 */
'use strict';
angular.module('lottery')
    .factory('SellerService', ['$http','$q', function ($http, $q) {
    return {
        fetchAllSellers: fetchAllSellers,
        fetchAllSellersFiltered: fetchAllSellersFiltered,
        fetchAllSellersSize:fetchAllSellersSize
    };


    function fetchAllSellers() {
        var deferred = $q.defer();
        $http.get("/api/seller/")
            .then(
                function (response) {
                    deferred.resolve(response.data);
                }, function (errResponse) {
                    console.error(errResponse);
                    deferred.reject(errResponse);
                });
        return deferred.promise;
    }

    function fetchAllSellersFiltered(pageno,itemsPerPage,state) {
        var deferred = $q.defer();
        $http.get("/api/seller/find/"+(pageno-1)+"/item/"+itemsPerPage+'/state/'+ state)
            .then(
                function (response) {
                    deferred.resolve(response.data);
                }, function (errResponse) {
                    console.error(errResponse);
                    deferred.reject(errResponse);
                });
        return deferred.promise;
    }

    function fetchAllSellersSize(state) {
        var deferred = $q.defer();
        $http.get("/api/seller/find/size/state/"+state)
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
