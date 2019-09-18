angular.module('lottery')
    .factory('ReadService', ['NgTableParams', function (NgTableParams) {
        return {
            fetchData: fetchData
        };

        function fetchData(api) {
            return new NgTableParams({
                count: 5,
                sorting: { id: "desc" }
            }, {
                counts: [5, 10, 15, 20, 25, 30, 40, 50, 100],
                paginationMaxBlocks: 5,
                paginationMinBlocks: 2,
                getData: function (params) {
                    return api.get(params.url()).$promise.then(function (data) {
                            if (data && data.content !== undefined){
                                params.total(data.totalElements);
                                return data.content;
                            }
                            return  [];
                        },
                        function (errorResponse) {
                            console.error(errorResponse);
                        });
                }
            });
        }

    }]);