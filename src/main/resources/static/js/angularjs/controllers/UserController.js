/**
 * Created by Dany on 09/05/2019.
 */
app.controller("userController", ['ReadService','$scope', 'UserService',function (ReadService, $scope,UserService) {
    $scope.global = {
        tableParams: null,
        stateFilter: [{ id: 0, title: "Bloke"}, { id: 1, title: "Tout"}, { id: 2, title: "Actif"}],
        api: $resource("/api/user")
    };
    $scope.usernameExist = false;
    $scope.message ='';
    $scope.username ='';
    $scope.suggestedUsername =[];

    $scope.isRole = function (roles, role){
        if (!roles || roles.length <= 0)
            return false;

        var result = roles.find(function (r) {
            return r.name === role;
        });
        return result!=null;
    };

    $scope.init = function (reading) {
        if (reading)
            $scope.global.tableParams = ReadService.fetchData($scope.global.api);
    };

    $scope.usernameChange = function (){
        $scope.suggestedUsername =[];
        if ($scope.username !== ''){
            fetchUser($scope.username);
        }
    };

    function fetchUser(username) {
        UserService.fetchUser(username)
            .then(
                function (d) {
                    if (d === null || d === undefined)
                        $scope.message = 'Ou pa gen akse pou ou reyalize aksyon sa';
                    else{
                        $scope.usernameExist = d.exist;
                        generateSuggestion();
                    }
                },
                function (errorResponse) {
                    console.error(errorResponse);
                })
    }

    function generateSuggestion() {
        if($scope.usernameExist){
            for (var i=0 ; i< 2; i++){
                var random = Math.floor(Math.random() * (100 - 1)) + 1;
                $scope.suggestedUsername.push($scope.username + random);
            }
        }
    }

}]);