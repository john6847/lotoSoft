/**
 * Created by Dany on 09/05/2019.
 */
app.controller("reportController", ['$http', 'ReportService','SellerService', 'ConfigurationService', '$scope', 'DTOptionsBuilder', function ($http, ReportService, SellerService, ConfigurationService, $scope, DTOptionsBuilder) {
    $scope.global = {
        sellers: [],
        selectedSeller: null,
        shifts: [],
        selectedShift: null,
        startDate: null,
        endDate: null,
        message: null
    };

    fetchAllSellers();
    fetchAllShifts();
    function fetchAllShifts() {
        ConfigurationService.fetchAllShifts()
            .then(
                function (d) {
                    if (d === null || d === undefined) {
                        $scope.message = 'Ou pa gen aksè pou ou reyalize aksyon sa';
                        $scope.global.shifts = [];
                    }
                    else{
                        $scope.global.shifts = d;
                    }
                },
                function (errorResponse) {
                    console.error(errorResponse);
                })

    }

    function fetchAllSellers() {
        SellerService.fetchAllSellers()
            .then(
                function (d) {
                    if (d === null || d === undefined)
                        $scope.global.sellers = [];
                    else
                        $scope.global.sellers = d;
                },
                function (errorResponse) {
                    console.error(errorResponse);
                })

    }

}]);