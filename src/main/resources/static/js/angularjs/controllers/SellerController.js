/**
 * Created by Dany on 09/05/2019.
 */
app.controller("sellerController", ['$http', 'SellerService','UserService','$scope','DTOptionsBuilder','Constants',function ($http, SellerService,UserService, $scope,DTOptionsBuilder,Constants) {
    $scope.sellers = [];

    $scope.haveUser = false;
    $scope.haveGroup = false;
    $scope.isParentSeller = false;
    $scope.useMonthlyPayment =false;
    $scope.pageno = 1;
    $scope.paymentType = Constants.PaymentType;
    $scope.itemsPerPage = 1000;
    $scope.state = 1;

    $scope.username = {
        sellerUsername :'',
        usernameExist: false,
        suggestedUsername: []
    };

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

    fetchAllSellers();

    $scope.usernameChange = function (){
        console.log($scope.username.sellerUsername)
        $scope.username.suggestedUsername =[];
        if ($scope.username.sellerUsername !== ''){
            fetchUser($scope.username.sellerUsername);
        }
    };

    $scope.getData = function () {
        $scope.start= $scope.pageno *$scope.itemsPerPage-$scope.itemsPerPage;

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


    function fetchUser(username) {
        UserService.fetchUser(username)
            .then(
                function (d) {
                    if (d === null || d === undefined)
                        $scope.message = 'Ou pa gen aksè pou ou reyalize aksyon sa';
                    else{
                        $scope.username.usernameExist = d.exist;
                        generateSuggestion();
                    }
                },
                function (errorResponse) {
                    console.error(errorResponse);
                });
    }

    function generateSuggestion() {
        if($scope.username.usernameExist){
            for (var i=0 ; i< 2; i++){
                var random = Math.floor(Math.random() * (1000 - 1)) + 1;
                $scope.username.suggestedUsername.push($scope.username.sellerUsername + random);
            }
        }
    }
}]);