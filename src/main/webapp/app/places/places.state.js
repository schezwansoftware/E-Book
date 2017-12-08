(function() {
    'use strict';

    angular
        .module('ebookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('places', {
            parent: 'app',
            url: '/places',
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'app/places/places.html',
                    controller: 'PlacesController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate,$translatePartialLoader) {
                    $translatePartialLoader.addPart('home');
                    return $translate.refresh();
                }]
            }
        });
    }
})();
