/**
 * Created by Dany on 12/05/2019.
 */
'use strict';
angular.module('lottery')
    .factory('NotificationService', ['$http','$q', function ($http, $q) {
    return {
        fetchAllNotifications: fetchAllNotifications
    };

    function fetchAllNotifications() {
        var deferred = $q.defer();
        $http.get("/api/notification/")
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
