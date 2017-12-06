(function() {
    'use strict';

    angular
        .module('ebookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('bootswatch', {
            parent: 'app',
            url: '/bootswatch',
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'app/components/bootswatch/bootswatchtheme.html',
                    controller: 'BootswatchController',
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
