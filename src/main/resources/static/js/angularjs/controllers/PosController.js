/**
 * Created by Dany on 09/05/2019.
 */
app.controller("posController", ['ReadService','NgTableParams', '$resource', 'PosService', '$scope', function (ReadService, NgTableParams, $resource, PosService, $scope) {
    $scope.global = {
        tableParams: null,
        stateFilter: [{ id: 0, title: "Bloke"}, { id: 1, title: "Tout"}, { id: 2, title: "Actif"}],
        api: $resource("/api/pos")
    };

    $scope.init = function (reading) {
        if (reading)
            $scope.global.tableParams = ReadService.fetchData($scope.global.api);
    };
}]);