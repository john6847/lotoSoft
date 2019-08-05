/**
 * Created by Dany on 09/05/2019.
 */
app.controller("bankController", ['$http', 'BankService','PosService', '$scope', 'DTOptionsBuilder', function ($http, BankService, PosService, $scope, DTOptionsBuilder) {
    $scope.banks = [];
    $scope.pos = [];
    $scope.serial = '';
    $scope.pageno = 1;
    $scope.totalCount = 0;
    $scope.itemsPerPage = 1000;
    $scope.state = 1;
    $scope.selectedSeller = null;

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


    fetchAllBank();

    $scope.getData = function () {
        $scope.banks = [];
        $scope.start = $scope.pageno * $scope.itemsPerPage - $scope.itemsPerPage;

        fetchAllBankFiltered($scope.pageno, $scope.itemsPerPage, $scope.state);
    };

    $scope.sellerChange = function (){
        if ($scope.selectedSeller){
            console.log($scope.selectedSeller)
            fetchPos($scope.selectedSeller);
        }
    };

    function fetchPos(selectedSeller) {
        PosService.fetchPosBySeller(selectedSeller)
            .then(
                function (d) {
                    if (d === null || d === undefined)
                        $scope.message = 'Ou pa gen akse pou ou reyalize aksyon sa';
                    else{
                        $scope.pos = d;
                    }
                },
                function (errorResponse) {
                    console.error(errorResponse);
                })
    }

    function fetchAllBank() {
        BankService.fetchAllBank()
            .then(
                function (d) {
                    $scope.banks = d;
                },
                function (errorResponse) {
                    console.error(errorResponse);
                })
    }

    function fetchAllBankFiltered(pageno, itemsPerPage, state) {
        BankService.fetchAllBankFiltered(pageno, itemsPerPage, state)
            .then(
                function (d) {
                    $scope.banks = d.content;
                },
                function (errorResponse) {
                    console.error(errorResponse);
                })
    }

}]);