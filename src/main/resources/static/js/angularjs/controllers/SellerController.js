/**
 * Created by Dany on 09/05/2019.
 */
app.controller("sellerController", ['NgTableParams', '$resource', '$http', 'SellerService','UserService','$scope','Constants',function (NgTableParams, $resource, $http, SellerService,UserService, $scope, Constants) {
    $scope.global = {
        tableParams: null,
        stateFilter: [{ id: 0, title: "Bloke"}, { id: 1, title: "Tout"}, { id: 2, title: "Actif"}],
        api:  $resource("/api/seller")
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

    $scope.global.tableParams = new NgTableParams({
        count: 5,
        sorting: { id: "asc" }
    }, {
        counts: [5, 10, 15, 20, 25, 30, 40, 50, 100],
        paginationMaxBlocks: 5,
        paginationMinBlocks: 2,
        getData: function (params) {
            return $scope.global.api.get(params.url()).$promise.then(function (data) {
                if (data && data.content !== undefined){
                    params.total(data.totalElements);
                    return data.content;
                }
                return  [];
            },
            function (errorResponse) {
                console.error(errorResponse);
            });
        }
    });


    $scope.usernameChange = function (){
        $scope.username.suggestedUsername =[];
        if ($scope.username.sellerUsername !== ''){
            fetchUser($scope.username.sellerUsername);
        }
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