/**
 * Created by Dany on 09/05/2019.
 */
app.controller("groupController", ['$http','$scope', 'GroupService','DTOptionsBuilder',function ($http, $scope,GroupService, DTOptionsBuilder  ) {
    $scope.groups = [];
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
                    type: 'text',
                    bRegex: true,
                    bSmart: true
                },{
                    type: 'text',
                    bRegex: true,
                    bSmart: true
                },
                {
                    type: 'select',
                    bRegex: false,
                    values: ['Wi','Non']
                },
            ]
        });

    fetchAllGroup();

    $scope.getData = function () {
        fetchAllGroupFiltered($scope.pageno,$scope.itemsPerPage,$scope.state);
    };

        function fetchAllGroup() {
            GroupService.fetchAllGroups()
                .then(
                    function (d) {
                        $scope.groups = createAddress(d);
                    },
                    function (errorResponse) {
                        console.error(errorResponse);
                    })
        }

        function fetchAllGroupFiltered(pageno, itemsPerPage, state) {
            GroupService.fetchAllGroupsFiltered(pageno, itemsPerPage, state)
                .then(
                    function (d) {
                        $scope.groups = createAddress(d.content);
                        console.log($scope.groups)
                    },
                    function (errorResponse) {
                        console.error(errorResponse);
                    })
        }

        function createAddress(groups) {
            for (var i=0 ; i< groups.length ; i++){
                groups[i].address.address = ' ';
                if (groups[i].address.city){
                    groups[i].address.address += groups[i].address.city +', '
                }
                if (groups[i].address.sector){
                    groups[i].address.address += groups[i].address.sector+', '
                }
                if (groups[i].address.country){
                    groups[i].address.address += groups[i].address.country +'\n '
                }
                if (groups[i].address.phone){
                    groups[i].address.address += groups[i].address.phone+' '
                }
            }
            return groups;
        }
}]);