/**
 * Created by Dany on 09/05/2019.
 */
app.controller("configurationController", ['$http', 'ConfigurationService','$scope', 'CombinationTypeService', 'CombinationService',function ($http, ConfigurationService, $scope, CombinationTypeService, CombinationService) {
    $scope.itemsPerPage= 5;

    $scope.selectType = {
        types: [
            {id: 1, name: 'Yon sèl'},
            {id: 2, name: 'Pa Gwoup'}
            ],
        changeTypes: [
            {id: 1, name: 'Pri Gwoup'},
            {id: 2, name: 'Bloke Gwoup'}
            ],
        type: null,
        changeType: null
    };



    $scope.shiftField = {
        selectedShift: null,
        shifts: [],
        resultShift: {
            id: 0,
            openTime: null,
            closeTime: null,
            enabled: true
        },
        message: null
    };

    $scope.combinationGroupField = {
        combinationTypes: null,
        blockCombinations: [],
        selectedCombinationType: null,
        resultCombinationGroup: {
            maxPrice: 0,
            combinationTypeId: 0,
            enabled: true
        },
        message: null
    };

    $scope.combinationField = {
        combinations: null,
        combination: null,
        selectedCombination: null,
        showList: false,
        enabled: false,
        changeState: false,
        changePrice: false,
        resultCombination: {
            id: 0,
            maxPrice: 0,
            enabled: true,
            changeState: false,
            changeMaxPrice: false
        },
        message: null
    };

    $scope.opptions = [
        {
            id: 1,
            color: 'success',
            code: 'Bo',
            name: 'Bolèt'
        }, {
            id: 2,
            color: 'danger',
            code: 'L3',
            name: 'Loto 3'
        }, {
            id: 3,
            color: 'warning',
            code: 'L4',
            name: 'Loto 4'
        }, {
            id: 4,
            color: 'warning',
            code: 'Op',
            name: 'Opsyon'
        }, {
            id: 5,
            color: 'warning',
            code: 'Ma',
            name: 'Maryaj'
        }, {
            id: 6,
            color: 'info',
            code: 'Ex',
            name: 'Extra'
        },
    ];

    $scope.getOptions = function (productId) {
        return $scope.opptions.find(function (item) {
            return item.id === productId;
        })
    };


    $scope.init = function(conf) {
        if (conf === 'shift'){
            fetchAllShifts();
        }
        if (conf === 'combination'){
            fetchAllCombinationTypes();
            fetchAllBlockedCombination();
        }
        $scope.selectType.type = $scope.selectType.types[0].id;
    };

    $scope.changeSelectType = function(){
        $scope.combinationField.selectedCombination  = null;
        $scope.combinationField.showList = false;
        $scope.combinationField.combinations = {};
        $scope.combinationField.combination = null;
        $scope.selectType.changeType = null;
        $scope.combinationGroupField.selectedCombinationType  = null;
    };

    $scope.combinationTypeChange = function() {
      $scope.selectType.changeType = $scope.selectType.changeTypes[0].id;
      $scope.combinationGroupField.resultCombinationGroup.combinationTypeId =  $scope.combinationGroupField.selectedCombinationType.id;
    };

    $scope.replaceString = function(str, newString) {
        return str.replace(/_/g, newString);
    };

    $scope.combinationChange = function (){
        $scope.combinationField.selectedCombination  = null;
        $scope.combinationGroupField.selectedCombinationType  = null;
        $scope.combinationField.message = null;
        $scope.combinationGroupField.message = null;
        $scope.combinationField.changePrice = false;
        $scope.combinationField.changeState = false;
        $scope.combinationField.resultCombination.changeMaxPrice = false;
        $scope.combinationField.resultCombination.changeState = false;
        fetchAllCombination($scope.combinationField.combination);
    };

    $scope.shiftChange = function (){
        $scope.shiftField.message = null;
    };

    $scope.updateVm = function(){
          for (var i = 0; i< $scope.shiftField.shifts.length; i++){
              if($scope.shiftField.shifts[i].openTime != null){
                  $scope.shiftField.shifts[i].openTime = new Date($scope.shiftField.shifts[i].openTime);
              }
              if($scope.shiftField.shifts[i].closeTime!= null){
                  $scope.shiftField.shifts[i].closeTime = new Date($scope.shiftField.shifts[i].closeTime)
              }
          }
    };

    $scope.selectCombination = function (id){
        $scope.combinationField.selectedCombination  = $scope.combinationField.combinations.find(function (combination) {
           return combination.id === id;
        });
        $scope.combinationField.resultCombination.id = id;
        $scope.combinationField.resultCombination.maxPrice = $scope.combinationField.selectedCombination.maxPrice;
        $scope.combinationField.resultCombination.enabled= $scope.combinationField.selectedCombination.enabled;
        $scope.combinationField.showList = false;
    };

    $scope.resetCombination = function (){
        $scope.combinationField.selectedCombination  = null;
        $scope.combinationField.showList = false;
        $scope.combinationField.combinations = {};
        $scope.combinationField.combination = null;
        $scope.combinationField.resultCombination.id = 0;
        $scope.combinationField.resultCombination.maxPrice = 0;
        $scope.combinationField.resultCombination.enabled = true;
        $scope.combinationField.changePrice = false;
        $scope.combinationField.changeState = false;
        $scope.combinationField.resultCombination.changeMaxPrice = false;
        $scope.combinationField.resultCombination.changeState = false;

    };

    $scope.resetCombinationGroup = function (){
        $scope.combinationGroupField.selectedCombinationType  = null;
        $scope.combinationGroupField.resultCombinationGroup = {
            maxPrice: 0,
            combinationTypeId: 0,
            enabled: true
        };
        $scope.selectType.type = null;
        $scope.selectType.changeType = null
    };

    $scope.resetShift = function (){
        $scope.shiftField.selectedShift= null;
        $scope.shiftField.resultShift = {
            id: 0,
            openTime: null,
            closeTime: null,
            enabled: true
        };
    };

    $scope.saveCombination = function () {
        if ($scope.combinationField.changePrice){
            $scope.combinationField.resultCombination.enabled = true;
            $scope.combinationField.resultCombination.changeMaxPrice = $scope.combinationField.changePrice;
            $scope.combinationField.resultCombination.changeState = false;
        }
        if ($scope.combinationField.changeState){
            $scope.combinationField.resultCombination.maxPrice = 0;
            $scope.combinationField.resultCombination.changeState = $scope.combinationField.changeState;
            $scope.combinationField.resultCombination.changeMaxPrice = false;
        }
        updateCombinationConfiguration($scope.combinationField.resultCombination);
    };

    $scope.saveCombinationGroup = function () {
        if ($scope.selectType.changeType <= 1){
            $scope.combinationGroupField.resultCombinationGroup.enabled = true;
        }

        if ($scope.selectType.changeType > 1){
            $scope.combinationGroupField.resultCombinationGroup.maxPrice = 0;
        }

        updateCombinationConfigurationGroup($scope.combinationGroupField.resultCombinationGroup);
    };

    $scope.saveShift = function () {
        $scope.shiftField.resultShift.id =  $scope.shiftField.selectedShift.id;
        $scope.shiftField.resultShift.openTime =  $scope.shiftField.selectedShift.openTime.toLocaleString();
        $scope.shiftField.resultShift.closeTime =  $scope.shiftField.selectedShift.closeTime.toLocaleString();
        updateShitConfiguration($scope.shiftField.resultShift);
    };

    function fetchAllCombination(combination) {
        ConfigurationService.fetchAllCombination(combination)
            .then(
                function (d) {
                    groupItem(d);
                    console.log(d)
                },
                function (errorResponse) {
                    console.error(errorResponse);
                })

    }

    function fetchAllShifts() {
        ConfigurationService.fetchAllShifts()
            .then(
                function (d) {
                    $scope.shiftField.shifts = d;
                    $scope.updateVm()
                },
                function (errorResponse) {
                    console.error(errorResponse);
                })

    }

    function updateCombinationConfiguration(combination) {
        ConfigurationService.updateCombinationConfiguration(combination)
            .then(
                function (d) {
                    $scope.resetCombination();
                    $scope.combinationField.message = d;
                    fetchAllBlockedCombination();
                },
                function (errorResponse) {
                    console.error(errorResponse);
                });
    }

    function updateCombinationConfigurationGroup(combination) {
        ConfigurationService.updateCombinationConfigurationGroup(combination)
            .then(
                function (d) {
                    $scope.resetCombinationGroup();
                    $scope.combinationGroupField.message = d;
                },
                function (errorResponse) {
                    console.error(errorResponse);
                });
    }

    function fetchAllCombinationTypes() {
        CombinationTypeService.fetchAllCombinationTypes()
            .then(
                function (d) {
                    $scope.combinationGroupField.combinationTypes = d.content;
                },
                function (errorResponse) {
                    console.error(errorResponse);
                })
    }

    function updateShitConfiguration(shift) {
        ConfigurationService.updateShitConfiguration(shift)
            .then(
                function (d) {
                    $scope.resetShift();
                    $scope.shiftField.message = d;
                },
                function (errorResponse) {
                    console.error(errorResponse);
                });
    }

    function groupItem(data){
        if (data){
            // $scope.combinationField.combinations= data.reduce(function(results, org) {
            //     (results[org.combinationId] = results[org.combinationId] || []).push(org);
            //     return results;
            // }, {});
            $scope.combinationField.combinations = data;
            $scope.combinationField.showList = true;
        } else {
            $scope.combinationField.combinations = [];
            $scope.combinationField.showList = false;
        }
    }

    function fetchAllBlockedCombination() {
        CombinationService.fetchAllBlockedCombination()
            .then(
                function (d) {
                    $scope.combinationGroupField.blockCombinations = d;
                    console.log(d)
                },
                function (errorResponse) {
                    console.error(errorResponse);
                });
    }


}]);