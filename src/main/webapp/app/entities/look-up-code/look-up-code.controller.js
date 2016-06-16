(function() {
    'use strict';

    angular
        .module('countryCodeLookupApp')
        .controller('LookUpCodeController', LookUpCodeController);

    LookUpCodeController.$inject = ['$scope', '$state', 'LookUpCode', 'LookUpUserCodes', 'User','PrivateCountryCode'];

    function LookUpCodeController ($scope, $state, LookUpCode, LookUpUserCodes, User, PrivateCountryCode) {
        var vm = this;

        vm.lookUpCodes = [];
        vm.users = User.query();
        vm.getUserCodes = getUserCodes;
        loadAll();


        function loadAll() {
            LookUpCode.query(function(result) {
                vm.lookUpCodes = result;
            });
        }
        
        function getUserCodes(userLogin){
            LookUpUserCodes.codes({user_login: userLogin});
        }

        function getAccount() {
            Principal.identity().then(function(account) {
                vm.account = account;
                vm.isAuthenticated = Principal.isAuthenticated;
            });
        }
    }
})();
