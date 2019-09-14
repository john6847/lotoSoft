/**
 * Created by Dany on 09/05/2019.
 */
app.controller("posController", ['$http', 'PosService', '$scope', 'DTOptionsBuilder', function ($http, PosService, $scope, DTOptionsBuilder) {

    $scope.global = {
        dtOptions: loadDT(),
        pos: [],

    }
    $scope.serial = '';
    $scope.pageno = 1;
    $scope.itemsPerPage = 1000;
    $scope.state = 1;
    // http://blog.ashwani.co.in/new/2015/12/07/How-to-use-Angular-Datatables.html

    fetchAllPos();


    $scope.getData = function () {
        fetchAllPosFiltered($scope.pageno, $scope.itemsPerPage, $scope.state);
    };

    function fetchAllPos() {
        PosService.fetchAllPos()
            .then(
                function (d) {
                    if (d === null || d === undefined)
                        $scope.global.pos = [];
                    else{
                        $scope.global.pos = d;
                        loadDT();
                    }

                },
                function (errorResponse) {
                    console.error(errorResponse);
                })
    }

    function loadDT() {
        return DTOptionsBuilder.newOptions()
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
    }

    function fetchAllPosFiltered(pageno, itemsPerPage, state) {
        PosService.fetchAllPosFiltered(pageno, itemsPerPage, state)
            .then(
                function (d) {
                    if (d === null || d === undefined)
                        $scope.global.pos = [];
                    else
                        $scope.global.pos = d.content;
                },
                function (errorResponse) {
                    console.error(errorResponse);
                })
    }

}]);