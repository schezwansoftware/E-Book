(function() {
    'use strict';

    angular
        .module('ebookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('user-certificates', {
            parent: 'entity',
            url: '/user-certificates',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ebookApp.userCertificates.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/user-certificates/user-certificates.html',
                    controller: 'UserCertificatesController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('userCertificates');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('user-certificates-detail', {
            parent: 'user-certificates',
            url: '/user-certificates/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ebookApp.userCertificates.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/user-certificates/user-certificates-detail.html',
                    controller: 'UserCertificatesDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('userCertificates');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'UserCertificates', function($stateParams, UserCertificates) {
                    return UserCertificates.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'user-certificates',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('user-certificates-detail.edit', {
            parent: 'user-certificates-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/user-certificates/user-certificates-dialog.html',
                    controller: 'UserCertificatesDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['UserCertificates', function(UserCertificates) {
                            return UserCertificates.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('user-certificates.new', {
            parent: 'user-certificates',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/user-certificates/user-certificates-dialog.html',
                    controller: 'UserCertificatesDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                userlogin: null,
                                certificateurl: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('user-certificates', null, { reload: 'user-certificates' });
                }, function() {
                    $state.go('user-certificates');
                });
            }]
        })
        .state('user-certificates.edit', {
            parent: 'user-certificates',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/user-certificates/user-certificates-dialog.html',
                    controller: 'UserCertificatesDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['UserCertificates', function(UserCertificates) {
                            return UserCertificates.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('user-certificates', null, { reload: 'user-certificates' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('user-certificates.delete', {
            parent: 'user-certificates',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/user-certificates/user-certificates-delete-dialog.html',
                    controller: 'UserCertificatesDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['UserCertificates', function(UserCertificates) {
                            return UserCertificates.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('user-certificates', null, { reload: 'user-certificates' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
