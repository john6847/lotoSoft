/**
 * Created by Dany on 09/05/2019.
 */
app.controller("groupController", ['NgTableParams', '$resource','$http','$scope', 'GroupService', function (NgTableParams, $resource, $http, $scope) {
    $scope.global = {
        tableParams: null,
        stateFilter: [{ id: 0, title: "Bloke"}, { id: 1, title: "Tout"}, { id: 2, title: "Actif"}],
        api:  $resource("/api/group")
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
                    return createAddress(data.content);
                }
                return  [];
            },
            function (errorResponse) {
                console.error(errorResponse);
            });
        }
    });


    function createAddress(groups) {
        if (groups!== null && groups!== undefined){
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
        }
        return groups;
    }
}]);