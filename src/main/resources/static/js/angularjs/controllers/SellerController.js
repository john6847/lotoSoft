/**
 * Created by Dany on 09/05/2019.
 */
app.controller("sellerController", ['$http', 'SellerService','$scope','DTOptionsBuilder','Constants',function ($http, SellerService, $scope,DTOptionsBuilder,Constants) {
    $scope.sellers = [];

    $scope.haveUser = false;
    $scope.haveGroup = false;
    $scope.isParentSeller = false;
    $scope.useMonthlyPayment =true;
    $scope.subSellerSelector = null;
    $scope.parentSellerSelector = null;
    $scope.pageno = 1;
    $scope.totalCount = 0;
    $scope.paymentType = Constants.PaymentType;

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
                    type: 'number',
                    bRegex: true,
                    bSmart: true
                },
                {
                    type: 'number',
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
                }]
        });


    $scope.isTrue = function (val){
        return val===true
    };

    $scope.isNull = function (val){
        return val===null
    };

    fetchAllSellers();

    $scope.change = function (){
        console.log($scope.haveUser)
    };

    $scope.getData = function () {
        $scope.seller=[];
        $scope.start= $scope.pageno *$scope.itemsPerPage-$scope.itemsPerPage;

        fetchAllSellerSize($scope.state);

        fetchAllSellerFiltered($scope.pageno,$scope.itemsPerPage,$scope.state);
    };

    function fetchAllSellers() {
        SellerService.fetchAllSellers()
            .then(
                function (d) {
                    if (d === null || d === undefined)
                        $scope.sellers = [];
                    else
                        $scope.sellers = d;
                },
                function (errorResponse) {
                    console.error(errorResponse);
                })

    }

    function fetchAllSellerFiltered(pageno,itemsPerPage,state) {
        SellerService.fetchAllSellersFiltered(pageno,itemsPerPage,state)
            .then(
                function (d) {
                    if (d === null || d === undefined)
                        $scope.sellers = [];
                    else
                        $scope.sellers = d.content;
                },
                function (errorResponse) {
                    console.error(errorResponse);
                })
    }

    function fetchAllSellerSize(state) {
        SellerService.fetchAllSellersSize(state)
            .then(
                function (d) {
                    if (d === 0 || d === null || d === undefined)
                        $scope.sellers = 0;
                    else
                        $scope.totalCount = d;
                },
                function (errorResponse) {
                    console.error(errorResponse);
                })
    }
}]);