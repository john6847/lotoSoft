/**
 * Created by Dany on 09/05/2019.
 */
app.controller("bankController", ['NgTableParams', '$resource',  '$http', 'BankService','PosService', '$scope', function (NgTableParams, $resource ,$http,  BankService, PosService, $scope) {
    $scope.pos = [];
    $scope.global={
        tableParams: null,
        stateFilter: [{ id: 0, title: "Bloke"}, { id: 1, title: "Tout"}, { id: 2, title: "Actif"}],
        api:  $resource("/api/bank")
    };
    $scope.serial = '';
    $scope.selectedSeller = null;
    $scope.selectedPos = null;

    $scope.global.tableParams = new NgTableParams({
    }, {
        counts: [5, 10, 15, 20, 25, 30, 40, 50, 100],
        paginationMaxBlocks: 5,
        paginationMinBlocks: 2,
        getData: function (params) {
            return $scope.global.api.get(params.url()).$promise.then(function (data) {
                params.total(data.totalElements);
                if (data.content === null || data.content === undefined)
                    return  [];
                else{
                    return  createAddress(data.content);
                }
            },
            function (errorResponse) {
                console.error(errorResponse);
            });

            }

    });

    $scope.sellerChange = function (updating){
        if ($scope.selectedSeller){
            fetchPos($scope.selectedSeller, updating);
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