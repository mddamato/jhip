(function() {
    'use strict';

    angular
        .module('countryCodeLookupApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
            .state('error', {
                parent: 'app',
                url: '/error',
                data: {
                    authorities: [],
                    pageTitle: 'Error!'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/layouts/error/error.html'
                    }
                }
            })
            .state('accessdenied', {
                parent: 'app',
                url: '/accessdenied',
                data: {
                    authorities: []
                },
                views: {
                    'content@': {
                        templateUrl: 'app/layouts/error/accessdenied.html'
                    }
                }
            });
    }
})();
