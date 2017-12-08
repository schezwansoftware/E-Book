(function () {
    'use strict';

    angular
        .module('ebookApp')
        .factory('States', States);

    States.$inject = ['$resource'];

    function States ($resource) {
        var service = $resource('api/state/:countrycode', {}, {
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
