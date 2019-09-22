/**
 * Created by Dany on 12/05/2019.
 */
'use strict';
angular.module('lottery')
    .factory('CombinationTypeService', ['$http','$q', function ($http, $q) {
    return {
        fetchAllCombinationTypes: fetchAllCombinationTypes
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


}]);
