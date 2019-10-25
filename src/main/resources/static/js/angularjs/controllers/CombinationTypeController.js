/**
 * Created by Dany on 09/05/2019.
 */
app.controller("combinationTypeController", ['ReadService','NgTableParams', '$resource', '$http', 'Constants','CombinationTypeService','$scope',function (ReadService, NgTableParams ,$resource, $http, Constants, CombinationTypeService, $scope) {
    $scope.global = {
        tableParams: null,
        stateFilter: [{ id: 0, title: "Bloke"}, { id: 1, title: "Tout"}, { id: 2, title: "Actif"}],
        api:  $resource("/api/combinationType")
    };
    $scope.constant = Constants.Products;

    $scope.init = function (reading) {
        if (reading)
            $scope.global.tableParams = ReadService.fetchData($scope.global.api);
    };
}]);