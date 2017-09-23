(function() {
    'use strict';
    angular
        .module('ebookApp')
        .factory('Book', Book);

    Book.$inject = ['$resource', 'DateUtils'];

    function Book ($resource, DateUtils) {
        var resourceUrl =  'api/books/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.released_date = DateUtils.convertLocalDateFromServer(data.released_date);
                        data.added_date = DateUtils.convertLocalDateFromServer(data.added_date);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.released_date = DateUtils.convertLocalDateToServer(copy.released_date);
                    copy.added_date = DateUtils.convertLocalDateToServer(copy.added_date);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.released_date = DateUtils.convertLocalDateToServer(copy.released_date);
                    copy.added_date = DateUtils.convertLocalDateToServer(copy.added_date);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();
