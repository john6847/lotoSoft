/**
 * Created by Dany on 09/05/2019.
 */
app.controller("userSuperAdminController", ['ReadService','$scope', '$resource',function (ReadService, $scope,$resource) {
    $scope.global = {
        tableParams: null,
        stateFilter: [{ id: 0, title: "Bloke"}, { id: 1, title: "Tout"}, { id: 2, title: "Actif"}],
        api: $resource("/api/user/superAdmin")
    };

    $scope.init = function (reading) {
        if (reading)
            $scope.global.tableParams = ReadService.fetchData($scope.global.api);
    };

    $scope.isRole = function (roles, role){
        if (!roles || roles.length <= 0)
            return false;

        var result = roles.find(function (r) {
            return r.name === role;
        });
        return result!=null;
    };

}]);