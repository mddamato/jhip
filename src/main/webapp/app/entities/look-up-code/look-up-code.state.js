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
        });
    }

})();
