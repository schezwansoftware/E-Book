(function() {
    'use strict';
    angular
        .module('ebookApp')
        .factory('FileDashboard', FileDashboard);

    FileDashboard.$inject = ['$resource'];

    function FileDashboard ($resource) {
        var resourceUrl =  'api/file-dashboards/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
