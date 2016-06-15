(function() {
    'use strict';
    angular
        .module('countryCodeLookupApp')
        .factory('MasterCountryCode', MasterCountryCode);

    MasterCountryCode.$inject = ['$resource'];

    function MasterCountryCode ($resource) {
        var resourceUrl =  'api/master-country-codes/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
