/**
 * Created by Dany on 12/05/2019.
 */
'use strict';
angular.module('lottery')
    .factory('ConfigurationService', ['$http', '$q', function ($http, $q) {
        return {
            fetchAllCombination: fetchAllCombination,
            updateCombinationConfiguration: updateCombinationConfiguration,
            fetchAllShifts: fetchAllShifts,
            updateShitConfiguration: updateShitConfiguration,
            updateCombinationConfigurationGroup: updateCombinationConfigurationGroup,
            fetchGlobalConfiguration: fetchGlobalConfiguration,
            saveGlobalConfiguration: saveGlobalConfiguration
        };

        function fetchAllCombination(combination) {
            var deferred = $q.defer();

            $http.get("/api/combination/find", {params: {combination: combination}})
                .then(
                    function (response) {
                        deferred.resolve(response.data);
                    }, function (errResponse) {
                        console.error(errResponse);
                        deferred.reject(errResponse);
                    });
            return deferred.promise;
        }

        function updateCombinationConfiguration(data) {
            var deferred = $q.defer();

            $http.put('/api/combination/update', JSON.stringify(data))
                .then(
                    function (response) {
                        deferred.resolve(response.data);
                    }, function (errResponse) {
                        console.error(errResponse);
                        deferred.reject(errResponse);
                    });
            return deferred.promise;
        }

        function updateCombinationConfigurationGroup(data) {
            var deferred = $q.defer();

            $http.put('/api/combination/group/update', JSON.stringify(data))
                .then(
                    function (response) {
                        deferred.resolve(response.data);
                    }, function (errResponse) {
                        console.error(errResponse);
                        deferred.reject(errResponse);
                    });
            return deferred.promise;
        }

        function updateShitConfiguration(data) {
            var deferred = $q.defer();

            $http.put('/api/shift/update', JSON.stringify(data))
                .then(
                    function (response) {
                        deferred.resolve(response.data);
                    }, function (errResponse) {
                        console.error(errResponse);
                        deferred.reject(errResponse);
                    });
            return deferred.promise;
        }

        function fetchAllShifts() {
            var deferred = $q.defer();

            $http.get("/api/shift/")
                .then(
                    function (response) {
                        deferred.resolve(response.data);
                    }, function (errResponse) {
                        console.error(errResponse);
                        deferred.reject(errResponse);
                    });
            return deferred.promise;
        }

        function fetchGlobalConfiguration() {
            var deferred = $q.defer();

            $http.get("/api/configuration/global")
                .then(
                    function (response) {
                        deferred.resolve(response.data);
                    }, function (errResponse) {
                        console.error(errResponse);
                        deferred.reject(errResponse);
                    });
            return deferred.promise;
        }

        function saveGlobalConfiguration(data) {
            var deferred = $q.defer();

            $http.post('/api/combination/global/save', JSON.stringify(data))
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
