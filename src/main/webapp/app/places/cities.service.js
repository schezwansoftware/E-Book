(function () {
    'use strict';

    angular
        .module('ebookApp')
        .factory('Cities', Cities);

    Cities.$inject = ['$resource'];

    function Cities ($resource) {
        var service = $resource('api/city/:countrycode/:state', {}, {
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
