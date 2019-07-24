/**
 * Created by Dany on 09/05/2019.
 */
app.controller("drawController", ['$http', 'DrawService','$scope','DTOptionsBuilder',function ($http, DrawService, $scope, DTOptionsBuilder) {
    $scope.draws = [];
    $scope.pageno = 1;
    $scope.totalCount = 0;
    $scope.itemsPerPage= 999999;
    $scope.selectedMonth = new Date().getMonth()+1;
    $scope.selectedYear = new Date().getFullYear();
    $scope.months = DrawService.months();
    $scope.days = DrawService.days($scope.selectedMonth, $scope.selectedYear);
    $scope.range = function (min,max) {
        var days = [];
        for (var i = 1; i<=max; i++) {
            days.push(i);
        }
        return days;
    };
    $scope.years = DrawService.years();
    $scope.day = 0;
    $scope.month = 0;
    $scope.year = 0;
    $scope.state = 1;

    $scope.dtOptions = DTOptionsBuilder.newOptions()
        .withDisplayLength(5)
        .withBootstrapOptions(
            {
                pagination:{
                    classes:{
                        ul: 'pagination pagination-sm'
                    }
                }
            }
        )
        .withColumnFilter({
            aoColumns: [
                {
                    type: 'number'
                },
                {
                    type: 'text',
                    bRegex: true,
                    bSmart: true
                },
                {
                    type: 'text',
                    bRegex: true,
                    bSmart: true
                },
                {
                    type: 'text',
                    bRegex: true,
                    bSmart: true
                },
                {
                    type: 'text',
                    bRegex: true,
                    bSmart: true
                },
                {
                    type: 'text',
                    bRegex: true,
                    bSmart: true
                },
                {
                    type: 'select',
                    bRegex: false,
                    values: ['Wi','Non']
                },
            ]
        });
    fetchAllDraws();
    $scope.getData = function () {
        // $scope.draws=[];
        // $scope.start= pageno*$scope.itemsPerPage-$scope.itemsPerPage;

        fetchAllDrawsFiltered($scope.pageno,$scope.itemsPerPage,$scope.state,$scope.day,$scope.month,$scope.year);
    };

    function fetchAllDraws() {
        DrawService.fetchAllDraws()
            .then(
                function (d) {
                   $scope.draws = d;
                },
                function (errorResponse) {
                    console.error(errorResponse);
                })
    }

    function fetchAllDrawsFiltered(pageno,itemsPerPage,state, day, month, year) {
        DrawService.fetchAllDrawsFiltered(pageno,itemsPerPage,state, day, month, year)
            .then(
                function (d) {
                   $scope.draws = d;
                },
                function (errorResponse) {
                    console.error(errorResponse);
                })
    }

}]);