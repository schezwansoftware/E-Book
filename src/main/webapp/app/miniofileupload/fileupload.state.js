(function() {
    'use strict';

    angular
        .module('ebookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('fileupload', {
                    parent: 'app',
                    url: '/fileupload',
                    data: {
                        authorities: ['ROLE_USER']
                    },
                    onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                        $uibModal.open({
                            templateUrl: 'app/miniofileupload/fileupload.html',
                            controller: 'FileUploadController',
                            controllerAs: 'vm',
                            backdrop: 'static',
                            size: 'lg',
                            resolve: {

                            }
                        }).result.then(function() {
                            $state.go('home', {}, { reload: false });
                        }, function() {
                            $state.go('home');
                        });
                    }]
                });
    }
})();



