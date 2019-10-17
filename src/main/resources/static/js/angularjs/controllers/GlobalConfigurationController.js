/**
 * Created by Dany on 09/05/2019.
 */
app.controller("globalConfigurationController", ['ConfigurationService','$scope',function (ConfigurationService, $scope) {
    $scope.globalConfiguration= null;
    $scope.global = {
        message: null,
        saving: false,
        updating: false
    };

    fetchGlobalConfiguration();

    $scope.saveGlobalConfiguration = function () {
        $scope.global.saving = true;
        ConfigurationService.saveGlobalConfiguration($scope.globalConfiguration)
            .then(
                function (d) {
                    $scope.global.saving = false;
                    $scope.global.message = d;
                },
                function (errorResponse) {
                    console.error(errorResponse);
                    $scope.global.saving = false;
                });
    };

    function fetchGlobalConfiguration() {

        ConfigurationService.fetchGlobalConfiguration()
            .then(
                function (d) {
                    $scope.global.updating = false;
                    if (d === null || d === undefined)
                        $scope.globalConfiguration = null;
                    else
                        $scope.globalConfiguration = d;
                },
                function (errorResponse) {
                    console.error(errorResponse);
                    $scope.global.updating = true;
                })

    }
}]);