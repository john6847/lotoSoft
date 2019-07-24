/**
 * Created by Dany on 09/05/2019.
 */
app.controller("userController", ['$http','$scope', 'UserService','DTOptionsBuilder',function ($http, $scope,UserService, DTOptionsBuilder  ) {
    $scope.users = [];
    $scope.pageno = 1;
    $scope.totalCount = 0;
    $scope.itemsPerPage= 1000;
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

    fetchAllUser();

    $scope.getData = function () {
        fetchAllUserFiltered($scope.pageno,$scope.itemsPerPage,$scope.state);
    };

        function fetchAllUser() {
            UserService.fetchAllUsers()
                .then(
                    function (d) {
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
                        $scope.users = d.content;
                    },
                    function (errorResponse) {
                        console.error(errorResponse);
                    })
        }

}]);