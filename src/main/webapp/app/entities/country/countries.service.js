(function() {
    'use strict';
    angular
        .module('ebookApp')
        .factory('Countries', Countries);

    Countries.$inject = ['$resource'];

    function Countries ($resource) {
        var resourceUrl =  'api/countries-external';

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
