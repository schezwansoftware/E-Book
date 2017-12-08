(function () {
    'use strict';

    angular
        .module('ebookApp')
        .factory('Places', Places);

    Places.$inject = ['$resource'];

    function Places ($resource) {
        var service = $resource('api/places/:countrycode/:postalcode', {}, {
            'query': {method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'save': { method:'POST' },
            'update': { method:'PUT' },
            'delete':{ method:'DELETE'}
        });

        return service;
    }
})();
