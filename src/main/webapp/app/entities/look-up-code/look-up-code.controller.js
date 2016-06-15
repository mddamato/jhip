(function() {
    'use strict';

    angular
        .module('countryCodeLookupApp')
        .controller('LookUpCodeController', LookUpCodeController);

    LookUpCodeController.$inject = ['$scope', '$state', 'LookUpCode', 'User','PrivateCountryCode'];

    function LookUpCodeController ($scope, $state, LookUpCode, User, PrivateCountryCode) {
        var vm = this;

        vm.lookUpCodes = [];
        vm.users = User.query();
        loadAll();


        function loadAll() {
            LookUpCode.query(function(result) {
                vm.lookUpCodes = result;
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
