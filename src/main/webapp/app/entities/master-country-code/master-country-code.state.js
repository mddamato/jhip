(function() {
    'use strict';

    angular
        .module('countryCodeLookupApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('master-country-code', {
            parent: 'entity',
            url: '/master-country-code',
            data: {
                authorities: ['ROLE_ADMIN'],
                pageTitle: 'MasterCountryCodes'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/master-country-code/master-country-codes.html',
                    controller: 'MasterCountryCodeController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('master-country-code-detail', {
            parent: 'entity',
            url: '/master-country-code/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'MasterCountryCode'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/master-country-code/master-country-code-detail.html',
                    controller: 'MasterCountryCodeDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'MasterCountryCode', function($stateParams, MasterCountryCode) {
                    return MasterCountryCode.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('master-country-code.new', {
            parent: 'master-country-code',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/master-country-code/master-country-code-dialog.html',
                    controller: 'MasterCountryCodeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                masterCountryCode: null,
                                masterCountryName: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('master-country-code', null, { reload: true });
                }, function() {
                    $state.go('master-country-code');
                });
            }]
        })
        .state('master-country-code.edit', {
            parent: 'master-country-code',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/master-country-code/master-country-code-dialog.html',
                    controller: 'MasterCountryCodeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['MasterCountryCode', function(MasterCountryCode) {
                            return MasterCountryCode.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('master-country-code', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('master-country-code.delete', {
            parent: 'master-country-code',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/master-country-code/master-country-code-delete-dialog.html',
                    controller: 'MasterCountryCodeDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['MasterCountryCode', function(MasterCountryCode) {
                            return MasterCountryCode.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('master-country-code', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
