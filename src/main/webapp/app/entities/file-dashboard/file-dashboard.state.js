(function() {
    'use strict';

    angular
        .module('ebookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('file-dashboard', {
            parent: 'entity',
            url: '/file-dashboard',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ebookApp.fileDashboard.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/file-dashboard/file-dashboards.html',
                    controller: 'FileDashboardController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('fileDashboard');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('file-dashboard-detail', {
            parent: 'file-dashboard',
            url: '/file-dashboard/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ebookApp.fileDashboard.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/file-dashboard/file-dashboard-detail.html',
                    controller: 'FileDashboardDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('fileDashboard');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'FileDashboard', function($stateParams, FileDashboard) {
                    return FileDashboard.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'file-dashboard',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('file-dashboard-detail.edit', {
            parent: 'file-dashboard-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/file-dashboard/file-dashboard-dialog.html',
                    controller: 'FileDashboardDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['FileDashboard', function(FileDashboard) {
                            return FileDashboard.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('file-dashboard.new', {
            parent: 'file-dashboard',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/file-dashboard/file-dashboard-dialog.html',
                    controller: 'FileDashboardDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                filename: null,
                                filesize: null,
                                createdby: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('file-dashboard', null, { reload: 'file-dashboard' });
                }, function() {
                    $state.go('file-dashboard');
                });
            }]
        })
        .state('file-dashboard.edit', {
            parent: 'file-dashboard',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/file-dashboard/file-dashboard-dialog.html',
                    controller: 'FileDashboardDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['FileDashboard', function(FileDashboard) {
                            return FileDashboard.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('file-dashboard', null, { reload: 'file-dashboard' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('file-dashboard.delete', {
            parent: 'file-dashboard',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/file-dashboard/file-dashboard-delete-dialog.html',
                    controller: 'FileDashboardDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['FileDashboard', function(FileDashboard) {
                            return FileDashboard.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('file-dashboard', null, { reload: 'file-dashboard' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
