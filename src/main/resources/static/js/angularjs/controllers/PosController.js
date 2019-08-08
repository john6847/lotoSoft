/**
 * Created by Dany on 09/05/2019.
 */
app.controller("posController", ['$http', 'PosService', '$scope', 'DTOptionsBuilder', function ($http, PosService, $scope, DTOptionsBuilder) {
    $scope.pos = [];
    $scope.serial = '';
    $scope.pageno = 1;
    $scope.itemsPerPage = 1000;
    $scope.state = 1;
    // http://blog.ashwani.co.in/new/2015/12/07/How-to-use-Angular-Datatables.html
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
        .withOption("destroy", true)
        .withOption('responsive', true)
        .withOption('scrollX', '100%')
        .withOption('deferRender', true)
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


    fetchAllPos();

    $scope.getData = function () {
        fetchAllPosFiltered($scope.pageno, $scope.itemsPerPage, $scope.state);
    };

    function fetchAllPos() {
        PosService.fetchAllPos()
            .then(
                function (d) {
                    if (d === null || d === undefined)
                        $scope.pos = [];
                    else
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
                    if (d === null || d === undefined)
                        $scope.pos = [];
                    else
                        $scope.pos = d.content;
                },
                function (errorResponse) {
                    console.error(errorResponse);
                })
    }

}]);