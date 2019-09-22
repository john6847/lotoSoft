/**
 * Created by Dany on 12/05/2019.
 */
'use strict';
angular.module('lottery')
    .factory('PosService', ['$http', '$q', function ($http, $q) {
        return {
            fetchPosBySeller : fetchPosBySeller
        };

        function fetchPosBySeller(id, updating) {
            var deferred = $q.defer();
            $http.get("/api/pos/find/seller/"+ id +'/'+updating)
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
