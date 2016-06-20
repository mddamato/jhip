(function() {
    'use strict';
    angular
        .module('countryCodeLookupApp')
        .factory('LookUpCode', LookUpCode);

    LookUpCode.$inject = ['$resource'];

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


    function GetPrivateCodesForLogin($resource) {
        var resourceUrl =  'api/privateCountryCodeList/:login';
        return $resource(resourceUrl, {},
        {
            'getPrivateCodesForLogin': { method: 'GET', isArray: true, },
        });
    }



})();
