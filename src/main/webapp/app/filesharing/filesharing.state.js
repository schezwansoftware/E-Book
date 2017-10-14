(function() {
    'use strict';

    angular
        .module('ebookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
            .state('fileshared', {
                parent: 'filedashboard',
                url: '/fileshared',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/filesharing/filesharing.html',
                        controller: 'FilesharedController',
                        controllerAs: 'vm',
                        backdrop: 'static',
                        size: 'lg',
                        resolve: {

                        }
                    }).result.then(function() {
                        $state.go('filedashboard', {}, { reload: true });
                    }, function() {
                        $state.go('filedashboard');
                    });
                }]
            });
    }
})();



