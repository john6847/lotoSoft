/**
 * Created by Dany on 12/05/2019.
 */
'use strict';
angular.module('lottery')
    .factory('DrawService', ['$http','$q', function ($http, $q) {
    return {
        months: months,
        days: days,
        years: years,
        fetchAllDraws: fetchAllDraws,
        fetchAllDrawsFiltered: fetchAllDrawsFiltered,
    };

    function months () {
        return[
            {id: 1, month: "Janvye"},
            {id: 2, month: "Fevriye"},
            {id: 3, month: "Mars"},
            {id: 4, month: "Avril"},
            {id: 5, month: "Me"},
            {id: 6, month: "Jen"},
            {id: 7, month: "Jiyè"},
            {id: 8, month: "Out"},
            {id: 9, month: "Septanm"},
            {id: 10, month: "Oktòb"},
            {id: 11, month: "Novanm"},
            {id: 12, month: "Desanm"}
        ]
    }

    function days (month, year) {

        var min = 1;
        var max = 30;
        if (month === 1 || month === 3 || month === 5 || month === 7 || month === 8 || month === 10 || month === 12)
        {
            max = 31;
        }
        if ( month === 4 || month === 6 || month === 9 || month === 11) {
            max = 30;
        }
        if (month === 2)
        {
            if (leapyear(year))
                max = 29;
            else
                max = 28
        }

        return{
            min: min,
            max: max
        }
    }
    function leapyear(year)
    {
        return (year % 100 === 0) ? (year % 400 === 0) : (year % 4 === 0);
    }
    function years()
    {
        var years = [];
        var start = 1970;
        for(var i = start; i<= new Date().getFullYear(); i++)
            years.push(i);
        return years;
    }

    function fetchAllDraws() {
        var deferred = $q.defer();
        $http.get("/api/draw/")
            .then(
                function (response) {
                    deferred.resolve(response.data);
                }, function (errResponse) {
                    console.error(errResponse);
                    deferred.reject(errResponse);
                });
        return deferred.promise;
    }

    function fetchAllDrawsFiltered(pageno,itemsPerPage,state, day, month, year) {
        var deferred = $q.defer();
        $http.get("/api/draw/find/"+(pageno-1)+"/item/"+itemsPerPage+'/state/'+ state
            +'/day/'+day+'/month/'+month+'/year/'+year)
            .then(
                function (response) {
                    deferred.resolve(response.data);
                }, function (errResponse) {
                    console.error(errResponse);
                    deferred.reject(errResponse);
                });
        return deferred.promise;
    }

}]);
