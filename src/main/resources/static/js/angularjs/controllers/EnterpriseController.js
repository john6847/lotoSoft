/**
 * Created by Dany on 09/05/2019.
 */
app.controller("enterpriseController", ['$http', 'EnterpriseService', '$scope', 'DTOptionsBuilder', function ($http, EnterpriseService, $scope, DTOptionsBuilder) {
    $scope.enterprises = [];
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


    fetchAllEnterprise();

    $scope.getData = function () {
        $scope.pos = [];
        $scope.start = $scope.pageno * $scope.itemsPerPage - $scope.itemsPerPage;


        fetchAllEnterpriseFiltered($scope.pageno, $scope.itemsPerPage, $scope.state);
    };

    function fetchAllEnterprise() {
        EnterpriseService.fetchAllEnterprise()
            .then(
                function (d) {
                    $scope.enterprises = d;
                    console.log(d);
                },
                function (errorResponse) {
                    console.error(errorResponse);
                })
    }

    function fetchAllEnterpriseFiltered(pageno, itemsPerPage, state) {
        EnterpriseService.fetchAllEnterpriseFiltered(pageno, itemsPerPage, state)
            .then(
                function (d) {
                    $scope.enterprises = d.content;
                    console.log(d);
                },
                function (errorResponse) {
                    console.error(errorResponse);
                })
    }



}]);