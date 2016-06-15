(function() {
    'use strict';

    angular
        .module('countryCodeLookupApp')
        .controller('PrivateCountryCodeController', PrivateCountryCodeController);

    PrivateCountryCodeController.$inject = ['$scope', '$state', 'PrivateCountryCode', 'Principal'];

    function PrivateCountryCodeController ($scope, $state, PrivateCountryCode, Principal) {
        var vm = this;

        vm.privateCountryCodes = [];

        loadAll();
        getAccount();

        function loadAll() {
            PrivateCountryCode.query(function(result) {
                vm.privateCountryCodes = result;
            });
        }

        function getAccount() {
            Principal.identity().then(function(account) {
                vm.account = account;
                vm.isAuthenticated = Principal.isAuthenticated;
            });
        }
    }




})();
