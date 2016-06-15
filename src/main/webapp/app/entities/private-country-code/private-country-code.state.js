(function() {
    'use strict';

    angular
        .module('countryCodeLookupApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('private-country-code', {
            parent: 'entity',
            url: '/private-country-code',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'PrivateCountryCodes'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/private-country-code/private-country-codes.html',
                    controller: 'PrivateCountryCodeController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('private-country-code-detail', {
            parent: 'entity',
            url: '/private-country-code/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'PrivateCountryCode'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/private-country-code/private-country-code-detail.html',
                    controller: 'PrivateCountryCodeDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'PrivateCountryCode', function($stateParams, PrivateCountryCode) {
                    return PrivateCountryCode.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('private-country-code.new', {
            parent: 'private-country-code',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/private-country-code/private-country-code-dialog.html',
                    controller: 'PrivateCountryCodeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                startDate: null,
                                endDate: null,
                                countryName: null,
                                countryCode: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('private-country-code', null, { reload: true });
                }, function() {
                    $state.go('private-country-code');
                });
            }]
        })
        .state('private-country-code.edit', {
            parent: 'private-country-code',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/private-country-code/private-country-code-dialog.html',
                    controller: 'PrivateCountryCodeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PrivateCountryCode', function(PrivateCountryCode) {
                            return PrivateCountryCode.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('private-country-code', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('private-country-code.delete', {
            parent: 'private-country-code',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/private-country-code/private-country-code-delete-dialog.html',
                    controller: 'PrivateCountryCodeDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['PrivateCountryCode', function(PrivateCountryCode) {
                            return PrivateCountryCode.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('private-country-code', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
