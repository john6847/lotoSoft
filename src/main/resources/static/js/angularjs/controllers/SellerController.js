/**
 * Created by Dany on 09/05/2019.
 */
app.controller("sellerController", ['ReadService','$resource', 'SellerService','UserService','$scope','Constants',function (ReadService, $resource, SellerService,UserService, $scope, Constants) {
    $scope.global = {
        tableParams: null,
        stateFilter: [{ id: 0, title: "Bloke"}, { id: 1, title: "Tout"}, { id: 2, title: "Actif"}],
        api:  $resource("/api/seller"),
        selectedSeller: null
    };
    $scope.haveUser = false;
    $scope.haveGroup = false;
    $scope.isParentSeller = false;
    $scope.useMonthlyPayment = false;
    $scope.paymentType = Constants.PaymentType;

    $scope.username = {
        sellerUsername :'',
        usernameExist: false,
        suggestedUsername: []
    };

    $scope.init = function (reading) {
        if (reading)
            $scope.global.tableParams = ReadService.fetchData($scope.global.api);
    };

    $scope.usernameChange = function (){
        $scope.username.suggestedUsername =[];
        if ($scope.username.sellerUsername !== ''){
            fetchUser($scope.username.sellerUsername);
        }
    };

    $scope.getVm = function (seller) {
        console.log(seller);
        $scope.global.selectedSeller = seller;
    };

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