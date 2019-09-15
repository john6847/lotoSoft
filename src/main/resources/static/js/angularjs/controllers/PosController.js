/**
 * Created by Dany on 09/05/2019.
 */
app.controller("posController", ['NgTableParams', '$resource', 'PosService', '$scope', function (NgTableParams, $resource, PosService, $scope) {
    $scope.global = {
        tableParams: null,
        stateFilter: [{ id: 0, title: "Bloke"}, { id: 1, title: "Tout"}, { id: 2, title: "Actif"}],
        Api: $resource("/api/pos")
    };

    $scope.global.tableParams = new NgTableParams({
        count: 5,
        sorting: { id: "asc" }
    }, {
        counts: [5, 10, 15, 20, 25, 30, 40, 50, 100],
        paginationMaxBlocks: 5,
        paginationMinBlocks: 2,
        getData: function (params) {
            return $scope.global.Api.get(params.url()).$promise.then(function (data) {
                params.total(data.content.length);
                if (data.content === null || data.content === undefined)
                    return  [];
                else{
                    return data.content;
                }
            },
            function (errorResponse) {
                console.error(errorResponse);
            });
        }
    });

}]);