/**
 * Created by Dany on 09/05/2019.
 */
app.controller("reportController", ['$http', 'ReportService','SellerService', 'GroupService', 'ConfigurationService', '$scope', 'DTOptionsBuilder', function ($http, ReportService, SellerService, GroupService, ConfigurationService, $scope, DTOptionsBuilder) {
    $scope.global = {
        sellers: [],
        selectedSeller: null,
        shifts: [],
        selectedGroups: null,
        groups: [],
        selectedShift: null,
        startDate: null,
        endDate: null,
        message: null,
        isGroup: false,
        salesReports: []
    };

    $scope.salesReport ={
        startDate: null,
        endDate: null,
        sellerId: null,
        shiftId: null,
        groupId: null
    };

    $scope.total ={};

    fetchAllSellers();
    fetchAllShifts();
    fetchAllGroups();

    $scope.buildReport = function () {
        obj = {};
      if ($scope.global.isGroup){
        $scope.salesReport.sellerId = null;
        obj.groupId = $scope.global.selectedGroups
      } else {
        $scope.salesReport.groupId = null;
        obj.sellerId = $scope.global.selectedSeller
      }
      obj.startDate = $scope.global.startDate.toISOString().substring(0, 10);
      obj.endDate = $scope.global.endDate.toISOString().substring(0, 10);
      obj.shiftId = $scope.global.selectedShift;
      console.log(obj);
      fetchSalesReport(obj);
    };

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
    function fetchAllGroups() {
        GroupService.fetchAllGroups()
            .then(
                function (d) {
                    if (d === null || d === undefined)
                        $scope.global.groups = [];
                    else
                        $scope.global.groups = d;
                },
                function (errorResponse) {
                    console.error(errorResponse);
                })

    }

    function fetchSalesReport(salesReport) {
        ReportService.fetchSalesReport(salesReport)
            .then(
                function (d) {
                    if (d === null || d === undefined)
                        $scope.global.salesReports = [];
                    else{
                        $scope.total = {};
                        $scope.global.salesReports = d;
                        calculateTotal(d);
                    }
                    console.log(d)
                },
                function (errorResponse) {
                    console.error(errorResponse);
                })

    }

    function calculateTotal(saleReportList) {
        if (saleReportList.length === 0){
            $scope.total= null;
            return;
        }

        saleReportList.foreach(function (sale) {
            $scope.total.sale_total+= sale.sale_total;
            $scope.total.sale_result+= sale.sale_result;
            $scope.total.amount_won+= sale.amount_won;
            $scope.total.net_sale+= sale.net_sale;
            $scope.total.salary+= sale.salary;
        })
    }
}]);