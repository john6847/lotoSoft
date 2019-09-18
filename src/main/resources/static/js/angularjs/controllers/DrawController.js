/**
 * Created by Dany on 09/05/2019.
 */
app.controller("drawController", ['ReadService','$resource', 'DrawService','$scope',function (ReadService, $resource, DrawService, $scope) {
    $scope.global = {
        tableParams: null,
        stateFilter: [{ id: 0, title: "Bloke"}, { id: 1, title: "Tout"}, { id: 2, title: "Actif"}],
        api:  $resource("/api/draw")
    };
    $scope.selectedMonth = new Date().getMonth()+1;
    $scope.selectedYear = new Date().getFullYear();
    $scope.months = DrawService.months();
    $scope.days = DrawService.days($scope.selectedMonth, $scope.selectedYear);
    $scope.range = function (min,max) {
        var days = [];
        for (var i = 1; i<=max; i++) {
            days.push(i);
        }
        return days;
    };
    $scope.years = DrawService.years();
    $scope.day = 0;
    $scope.month = 0;
    $scope.year = 0;
    $scope.state = 1;

    $scope.init = function (reading) {
        if (reading)
            $scope.global.tableParams = ReadService.fetchData($scope.global.api);
    };

    $scope.replaceString = function(str, newString) {
        return str.replace(/_/g, newString);
    };
}]);