(function() {
    'use strict';
    angular
        .module('countryCodeLookupApp')
        .factory('PrivateCountryCode', PrivateCountryCode);

    PrivateCountryCode.$inject = ['$resource', 'DateUtils'];

    function PrivateCountryCode ($resource, DateUtils) {
        var resourceUrl =  'api/private-country-codes/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.startDate = DateUtils.convertDateTimeFromServer(data.startDate);
                        data.endDate = DateUtils.convertDateTimeFromServer(data.endDate);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' },

        });
    }
})();
