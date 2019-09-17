/**
 * Created by Dany on 09/05/2019.
 */
app.controller("posController", ['NgTableParams', '$resource', 'PosService', '$scope', function (NgTableParams, $resource, PosService, $scope) {
    $scope.global = {
        tableParams: null,
        stateFilter: [{ id: 0, title: "Bloke"}, { id: 1, title: "Tout"}, { id: 2, title: "Actif"}],
        api: $resource("/api/pos")
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

}]);