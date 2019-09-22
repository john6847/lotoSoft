/**
 * Created by Dany on 09/05/2019.
 */


var app = angular.module("lottery", ["ngTable",'angularUtils.directives.dirPagination','ngMaterial','ngAnimate', 'datatables','datatables.columnfilter','datatables.bootstrap', 'ngResource', 'ui.bootstrap', 'ngStomp'])
    .filter('customUserDateFilter', function($filter) {
        return function(values, dateString) {
            var filtered = [];

            if(typeof values != 'undefined' && typeof dateString != 'undefined') {
                angular.forEach(values, function(value) {
                    if($filter('date')(value.Date).indexOf(dateString) >= 0) {
                        filtered.push(value);
                    }
                });
            }

            return filtered;
        }
    });