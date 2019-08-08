/**
 * Created by Dany on 09/05/2019.
 */
app.controller("userSuperAdminController", ['$http','$scope', 'UserService','DTOptionsBuilder',function ($http, $scope,UserService, DTOptionsBuilder  ) {
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

    fetchAllUsersSuperAdmin();

    $scope.getData = function () {
        fetchAllUsersFilteredSuperAdmin($scope.pageno,$scope.itemsPerPage,$scope.state);
    };

        function fetchAllUsersSuperAdmin() {
            UserService.fetchAllUsersSuperAdmin()
                .then(
                    function (d) {
                        if (d === null || d === undefined)
                            $scope.users = [];
                        else
                            $scope.users = d;

                        console.log(d)
                    },
                    function (errorResponse) {
                        console.error(errorResponse);
                    })
        }

        function fetchAllUsersFilteredSuperAdmin(pageno, itemsPerPage, state) {
            UserService.fetchAllUsersFilteredSuperAdmin(pageno, itemsPerPage, state)
                .then(
                    function (d) {
                        if (d === null || d === undefined)
                            $scope.users = [];
                        else
                            $scope.users = d.content;
                        console.log(d)
                    },
                    function (errorResponse) {
                        console.error(errorResponse);
                    })
        }
}]);