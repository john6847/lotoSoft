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
    // $scope.product = 1;
    $scope.isBolet = false;
    $scope.constant = Constants.Products;

    $scope.isTrue = function (val){
        return val===true
    };

    $scope.change = function (){
        $scope.isBolet = parseInt($scope.product) === parseInt(Constants.Products[0].Id);
    };

    $scope.isNull = function (val){
        return val===null
    };
    // fetchAllProducts();
    $scope.getData = function (pageno) {
        $scope.combinationTypes=[];
        $scope.start= pageno*$scope.itemsPerPage-$scope.itemsPerPage;

        fetchAllCombinationTypesSize($scope.state);

        fetchAllCombinationTypesFiltered(pageno,$scope.itemsPerPage,$scope.state,$scope.day,$scope.month,$scope.year);
    };

    function fetchAllCombinationTypes() {
        CombinationTypeService.fetchAllCombinationTypes()
            .then(
                function (d) {
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
                   $scope.combinationTypes = d.content;
                    console.log(d)
                },
                function (errorResponse) {
                    console.error(errorResponse);
                })
    }

    function fetchAllCombinationTypesSize(state) {
        CombinationTypeService.fetchAllCombinationTypesSize(state)
            .then(
                function (d) {
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
                   $scope.products = d;
                },
                function (errorResponse) {
                    console.error(errorResponse);
                })
    }
}]);