(function () {
    'use strict';

    angular
        .module('countryCodeLookupApp')
        .factory('Register', Register);

    Register.$inject = ['$resource'];

    function Register ($resource) {
        return $resource('api/register', {}, {});
    }
})();
