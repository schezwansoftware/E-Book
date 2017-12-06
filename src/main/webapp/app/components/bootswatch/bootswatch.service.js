(function () {
    'use strict';

    angular
        .module('ebookApp')
        .factory('BootSwatchService', BootSwatchService);

    BootSwatchService.$inject = ['$resource'];

    function BootSwatchService ($resource) {
        var service = $resource('app/components/bootswatch/themes.json', {}, {
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
