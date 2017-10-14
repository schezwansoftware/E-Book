(function() {
    'use strict';

    angular
        .module('ebookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('filesharing', {
            parent: 'entity',
            url: '/filesharing',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ebookApp.filesharing.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/filesharing/filesharings.html',
                    controller: 'FilesharingController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('filesharing');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('filesharing-detail', {
            parent: 'filesharing',
            url: '/filesharing/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ebookApp.filesharing.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/filesharing/filesharing-detail.html',
                    controller: 'FilesharingDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('filesharing');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Filesharing', function($stateParams, Filesharing) {
                    return Filesharing.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'filesharing',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('filesharing-detail.edit', {
            parent: 'filesharing-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/filesharing/filesharing-dialog.html',
                    controller: 'FilesharingDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Filesharing', function(Filesharing) {
                            return Filesharing.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('filesharing.new', {
            parent: 'filesharing',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/filesharing/filesharing-dialog.html',
                    controller: 'FilesharingDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                filename: null,
                                sharedby: null,
                                sharedto: null,
                                sharedon: null,
                                verified: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('filesharing', null, { reload: 'filesharing' });
                }, function() {
                    $state.go('filesharing');
                });
            }]
        })
        .state('filesharing.edit', {
            parent: 'filesharing',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/filesharing/filesharing-dialog.html',
                    controller: 'FilesharingDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Filesharing', function(Filesharing) {
                            return Filesharing.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('filesharing', null, { reload: 'filesharing' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('filesharing.delete', {
            parent: 'filesharing',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/filesharing/filesharing-delete-dialog.html',
                    controller: 'FilesharingDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Filesharing', function(Filesharing) {
                            return Filesharing.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('filesharing', null, { reload: 'filesharing' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
