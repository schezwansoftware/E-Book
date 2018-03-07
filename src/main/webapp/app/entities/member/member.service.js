(function() {
    'use strict';
    angular
        .module('ebookApp')
        .factory('Member', Member);

    Member.$inject = ['$resource', 'DateUtils'];

    function Member ($resource, DateUtils) {
        var resourceUrl =  'api/members/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.join_date = DateUtils.convertDateTimeFromServer(data.join_date);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
