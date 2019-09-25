/**
 * Created by Dany on 09/05/2019.
 */
app.controller("enterpriseController", ['ReadService', '$resource', 'EnterpriseService', '$scope', function (ReadService, $resource,EnterpriseService, $scope) {
    $scope.serial = '';
    $scope.global = {
        tableParams: null,
        stateFilter: [{ id: 0, title: "Bloke"}, { id: 1, title: "Tout"}, { id: 2, title: "Actif"}],
        api:  $resource("/api/enterprise")
    };

    $scope.init = function (reading) {
        if (reading)
            $scope.global.tableParams = ReadService.fetchData($scope.global.api);
    };

}]);