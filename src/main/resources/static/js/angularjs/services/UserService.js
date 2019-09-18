/**
 * Created by Dany on 12/05/2019.
 */
'use strict';
angular.module('lottery')
    .factory('UserService', ['$http','$q', function ($http, $q) {
    return {
        fetchAllUsersLogged: fetchAllUsersLogged,
        fetchUser: fetchUser
    };

    function fetchAllUsersLogged() {
        var deferred = $q.defer();
        $http.get("/api/user/connected")
            .then(
                function (response) {
                    deferred.resolve(response.data);
                }, function (errResponse) {
                    console.error(errResponse);
                    deferred.reject(errResponse);
                });
        return deferred.promise;
    }

    function fetchUser(username) {

        var deferred = $q.defer();
        $http.get("/api/user/exist", {params: {username: username}})
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
