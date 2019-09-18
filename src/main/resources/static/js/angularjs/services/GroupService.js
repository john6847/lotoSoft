/**
 * Created by Dany on 12/05/2019.
 */
'use strict';
angular.module('lottery')
    .factory('GroupService', ['$http','$q', function ($http, $q) {
    return {
        fetchAllGroups: fetchAllGroups
    };

    function fetchAllGroups() {
        var deferred = $q.defer();
        $http.get("/api/group/")
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
