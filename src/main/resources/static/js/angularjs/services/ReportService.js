/**
 * Created by Dany on 12/05/2019.
 */
'use strict';
angular.module('lottery')
    .factory('ReportService', ['$http', '$q', function ($http, $q) {
        return {
            fetchSalesReport: fetchSalesReport
        };

        function fetchSalesReport(salesReport) {
            var deferred = $q.defer();
            $http.put("/api/sales/report",  JSON.stringify(salesReport))
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
