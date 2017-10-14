(function() {
    'use strict';
    angular
        .module('ebookApp')
        .factory('Filesharing', Filesharing);

    Filesharing.$inject = ['$resource', 'DateUtils'];

    function Filesharing ($resource, DateUtils) {
        var resourceUrl =  'api/filesharings/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.sharedon = DateUtils.convertLocalDateFromServer(data.sharedon);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.sharedon = DateUtils.convertLocalDateToServer(copy.sharedon);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.sharedon = DateUtils.convertLocalDateToServer(copy.sharedon);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();
