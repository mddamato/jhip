(function() {
    'use strict';
    angular
        .module('countryCodeLookupApp')
        .factory('LookUpCode', LookUpCode)
        .factory('LookUpUserCodes', LookUpUserCodes)

    LookUpCode.$inject = ['$resource'];
    LookUpUserCodes.$inject = ['$resource'];

    function LookUpCode ($resource) {
        var resourceUrl =  'api/look-up-codes/:id';

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

    function LookUpUserCodes ($resource) {
        var resourceUrl = "api/look-up-codes/user/:user_login";
        return $resource(resourceUrl, {}, {
            'codes': { method: 'GET', isArray: true}
        });
    }
})();
