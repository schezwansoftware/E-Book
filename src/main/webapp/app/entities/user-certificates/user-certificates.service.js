(function() {
    'use strict';
    angular
        .module('ebookApp')
        .factory('UserCertificates', UserCertificates);

    UserCertificates.$inject = ['$resource'];

    function UserCertificates ($resource) {
        var resourceUrl =  'api/user-certificates/:id';

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
