/**
 * Created by Dany on 12/05/2019.
 */
'use strict';
angular.module('lottery')
    .factory('UserService', ['$http','$q', function ($http, $q) {
    return {
        fetchAllUsers: fetchAllUsers,
        fetchAllUsersSuperAdmin: fetchAllUsersSuperAdmin,
        fetchAllUsersFiltered: fetchAllUsersFiltered,
        fetchAllUsersFilteredSuperAdmin: fetchAllUsersFilteredSuperAdmin,
        fetchUser: fetchUser
    };


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

    function fetchAllUsers() {

        var deferred = $q.defer();
        $http.get("/api/user/")
            .then(
                function (response) {
                    deferred.resolve(response.data);
                }, function (errResponse) {
                    console.error(errResponse);
                    deferred.reject(errResponse);
                });
        return deferred.promise;
    }

    function fetchAllUsersSuperAdmin() {
        var deferred = $q.defer();
        $http.get("/api/user/superAdmin")
            .then(
                function (response) {
                    deferred.resolve(response.data);
                }, function (errResponse) {
                    console.error(errResponse);
                    deferred.reject(errResponse);
                });
        return deferred.promise;
    }

    function fetchAllUsersFiltered(pageno,itemsPerPage,state) {
        var deferred = $q.defer();
        $http.get("/api/user/find/"+(pageno-1)+"/item/"+itemsPerPage+'/state/'+ state)
            .then(
                function (response) {
                    deferred.resolve(response.data);
                }, function (errResponse) {
                    console.error(errResponse);
                    deferred.reject(errResponse);
                });
        return deferred.promise;
    }

    function fetchAllUsersFilteredSuperAdmin(pageno,itemsPerPage,state) {
        var deferred = $q.defer();
        $http.get("/api/user/find/"+(pageno-1)+"/item/"+itemsPerPage+'/state/'+ state+'/superAdmin')
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
