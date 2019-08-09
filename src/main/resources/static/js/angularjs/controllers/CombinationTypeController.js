/**
 * Created by Dany on 09/05/2019.
 */
app.controller("combinationTypeController", ['$http', 'Constants','CombinationTypeService','$scope',function ($http, Constants, CombinationTypeService, $scope) {
    $scope.combinationTypes = [];
    $scope.pageno = 1;
    $scope.totalCount = 0;
    $scope.itemsPerPage= 10;
    $scope.state = 1;
    $scope.products = [];
    $scope.constant = Constants.Products;

    $scope.getData = function (pageno) {
        $scope.combinationTypes=[];

        $scope.start= pageno*$scope.itemsPerPage-$scope.itemsPerPage;

        fetchAllCombinationTypesSize($scope.state);

        fetchAllCombinationTypesFiltered(pageno,$scope.itemsPerPage,$scope.state);
    };
    fetchAllCombinationTypes();

    function fetchAllCombinationTypes() {
        CombinationTypeService.fetchAllCombinationTypes()
            .then(
                function (d) {
                    if (d === null || d === undefined)
                        $scope.combinationTypes = [];
                    else
                        $scope.combinationTypes = d;
                },
                function (errorResponse) {
                    console.error(errorResponse);
                })
    }

    function fetchAllCombinationTypesFiltered(pageno,itemsPerPage,state) {
        CombinationTypeService.fetchAllCombinationTypesFiltered(pageno,itemsPerPage,state)
            .then(
                function (d) {
                    if (d === null || d === undefined)
                        $scope.combinationTypes = [];
                    else
                       $scope.combinationTypes = d.content;
                },
                function (errorResponse) {
                    console.error(errorResponse);
                })
    }

    function fetchAllCombinationTypesSize(state) {
        CombinationTypeService.fetchAllCombinationTypesSize(state)
            .then(
                function (d) {
                    if (d=== 0 || d === null || d === undefined)
                        $scope.totalCount = 0;
                    else
                        $scope.totalCount = d;
                },
                function (errorResponse) {
                    console.error(errorResponse);
                })
    }

    function fetchAllProducts() {
        CombinationTypeService.fetchAllProducts()
            .then(
                function (d) {
                    if (d === null || d === undefined)
                        $scope.products = [];
                    else
                        $scope.products = d;

                },
                function (errorResponse) {
                    console.error(errorResponse);
                })
    }
}]);