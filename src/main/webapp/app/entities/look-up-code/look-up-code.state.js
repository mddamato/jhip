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


        .state('look-up-code.list', {
            parent: 'look-up-code',
            url: '/list',
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
                }).result.then(

                function() {
                    $state.go('look-up-code', null, { reload: true });
                },
                function() {
                    $state.go('look-up-code');
                });
            }]
        });
    }

})();
