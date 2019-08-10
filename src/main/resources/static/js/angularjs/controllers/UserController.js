/**
 * Created by Dany on 09/05/2019.
 */
app.controller("userController", ['$http','$scope', 'UserService','DTOptionsBuilder',function ($http, $scope,UserService, DTOptionsBuilder  ) {
    $scope.users = [];
    $scope.pageno = 1;
    $scope.totalCount = 0;
    $scope.itemsPerPage= 1000;
    $scope.state = 1;
    $scope.usernameExist = false;
    $scope.message ='';
    $scope.username ='';
    $scope.suggestedUsername =[];

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
                    type: 'select',
                    bRegex: false,
                    values: ['Wi','Non']
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
                },
                {
                    type: 'select',
                    bRegex: false,
                    values: ['Wi','Non']
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



    $scope.isRole = function (roles, role){
        if (!roles || roles.length <= 0)
            return false;

        var result = roles.find(function (r) {
            return r.name === role;
        });
        return result!=null;
    };

    $scope.usernameChange = function (){
        $scope.suggestedUsername =[];
        if ($scope.username !== ''){
            fetchUser($scope.username);
        }
    };

    fetchAllUser();

    $scope.getData = function () {
        console.log("Get Data")
        fetchAllUserFiltered($scope.pageno,$scope.itemsPerPage,$scope.state);
    };

    function fetchAllUser() {
        UserService.fetchAllUsers()
            .then(
                function (d) {
                    if (d === null || d === undefined)
                        $scope.users = [];
                    else
                        $scope.users = d;
                },
                function (errorResponse) {
                    console.error(errorResponse);
                })
    }

    function fetchAllUserFiltered(pageno, itemsPerPage, state) {
        UserService.fetchAllUsersFiltered(pageno, itemsPerPage, state)
            .then(
                function (d) {
                    if (d === null || d === undefined)
                        $scope.users = [];
                    else
                        $scope.users = d.content;
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