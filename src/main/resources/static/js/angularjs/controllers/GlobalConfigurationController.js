/**
 * Created by Dany on 09/05/2019.
 */
app.controller("globalConfigurationController", ['ConfigurationService','$scope',function (ConfigurationService, $scope) {
    $scope.globalConfiguration= null;

    fetchGlobalConfiguration();

    function fetchGlobalConfiguration() {
        ConfigurationService.fetchGlobalConfiguration()
            .then(
                function (d) {
                    if (d === null || d === undefined)
                        $scope.globalConfiguration = null;
                    else
                        $scope.globalConfiguration = d;

                    console.log(d)
                },
                function (errorResponse) {
                    console.error(errorResponse);
                })

    }
}]);