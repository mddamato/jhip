(function() {
    'use strict';

    angular
        .module('countryCodeLookupApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('look-up-code', {
            parent: 'entity',
            url: '/look-up-code',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'LookUpCodes'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/look-up-code/look-up-codes.html',
                    controller: 'LookUpCodeController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('look-up-code-detail', {
            parent: 'entity',
            url: '/look-up-code/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'LookUpCode'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/look-up-code/look-up-code-detail.html',
                    controller: 'LookUpCodeDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'LookUpCode', function($stateParams, LookUpCode) {
                    return LookUpCode.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('look-up-code.new', {
            parent: 'look-up-code',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/look-up-code/look-up-code-dialog.html',
                    controller: 'LookUpCodeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('look-up-code', null, { reload: true });
                }, function() {
                    $state.go('look-up-code');
                });
            }]
        })
        .state('look-up-code.edit', {
            parent: 'look-up-code',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/look-up-code/look-up-code-dialog.html',
                    controller: 'LookUpCodeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['LookUpCode', function(LookUpCode) {
                            return LookUpCode.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('look-up-code', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('look-up-code.delete', {
            parent: 'look-up-code',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/look-up-code/look-up-code-delete-dialog.html',
                    controller: 'LookUpCodeDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['LookUpCode', function(LookUpCode) {
                            return LookUpCode.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('look-up-code', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
