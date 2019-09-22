/**
 * Created by Dany on 12/05/2019.
 */
'use strict';
angular.module('lottery')
    .factory('SellerService', ['$http','$q', function ($http, $q) {
    return {
        fetchAllSellers: fetchAllSellers
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

}]);
