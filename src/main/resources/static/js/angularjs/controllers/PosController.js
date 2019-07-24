/**
 * Created by Dany on 09/05/2019.
 */
app.controller("posController", ['$http', 'PosService', '$scope', 'DTOptionsBuilder', function ($http, PosService, $scope, DTOptionsBuilder) {
    $scope.pos = [];
    $scope.serial = '';
    $scope.pageno = 1;
    $scope.totalCount = 0;
    $scope.itemsPerPage = 1000;
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
                    type: 'select',
                    bRegex: false,
                    values: ['Wi','Non']
                },
                {
                    type: 'select',
                    bRegex: false,
                    values: ['Wi','Non']
                }
            ]
        });



    $scope.isTrue = function (val) {
        return val === true
    };

    $scope.isNull = function (val) {
        return val === null
    };


    fetchAllPos();

    $scope.getData = function () {
        $scope.pos = [];
        $scope.start = $scope.pageno * $scope.itemsPerPage - $scope.itemsPerPage;

        fetchAllPosSize($scope.state);

        fetchAllPosFiltered($scope.pageno, $scope.itemsPerPage, $scope.state);
    };

    function fetchAllPos() {
        PosService.fetchAllPos()
            .then(
                function (d) {
                    $scope.pos = d;
                },
                function (errorResponse) {
                    console.error(errorResponse);
                })
    }

    function fetchAllPosFiltered(pageno, itemsPerPage, state) {
        PosService.fetchAllPosFiltered(pageno, itemsPerPage, state)
            .then(
                function (d) {
                    $scope.pos = d.content;
                },
                function (errorResponse) {
                    console.error(errorResponse);
                })
    }

    function fetchAllPosSize(state) {
        PosService.fetchAllPosSize(state)
            .then(
                function (d) {
                    $scope.totalCount = d;
                },
                function (errorResponse) {
                    console.error(errorResponse);
                })
    }


}]);