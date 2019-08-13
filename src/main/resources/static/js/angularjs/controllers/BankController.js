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
                }
            ]
        });


    fetchAllBank();

    $scope.getData = function () {
        $scope.start = $scope.pageno * $scope.itemsPerPage - $scope.itemsPerPage;

        fetchAllBankFiltered($scope.pageno, $scope.itemsPerPage, $scope.state);
    };

    $scope.sellerChange = function (updatiing){
        if ($scope.selectedSeller){
            fetchPos($scope.selectedSeller, updatiing);
        }
    };

    function fetchPos(selectedSeller, updating) {
        PosService.fetchPosBySeller(selectedSeller, updating)
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
                    if (d === null || d === undefined){
                        $scope.message = 'Ou pa gen akse pou ou reyalize aksyon sa';
                        $scope.banks = [];
                    }
                    else{
                        $scope.banks = createAddress(d);
                    }
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
                    if (d === null || d === undefined){
                        $scope.message = 'Ou pa gen akse pou ou reyalize aksyon sa';
                        $scope.banks = [];
                    }
                    else{
                        $scope.banks = createAddress(d.content);
                    }
                },
                function (errorResponse) {
                    console.error(errorResponse);
                })
    }

    function createAddress(banks) {
        if (!banks) {
            return  [];
        }
        for (var i=0 ; i<banks.length ; i++){
            if (banks[i].address){
                banks[i].address.address = ' ';
                if (banks[i].address.sector){
                    banks[i].address.address += banks[i].address.sector+', '
                }
                if (banks[i].address.city){
                    banks[i].address.address += banks[i].address.city +', '
                }
                if (banks[i].address.region){
                    banks[i].address.address += banks[i].address.region +'\n '
                }
            }
        }
        return banks;
    }

}]);