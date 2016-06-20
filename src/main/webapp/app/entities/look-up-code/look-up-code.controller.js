(function()
{
    'use strict';

    angular
        .module('countryCodeLookupApp')
        .controller('LookUpCodeController', LookUpCodeController);

    LookUpCodeController.$inject = ['$scope', '$state', 'LookUpCode', 'User', 'PrivateCountryCode'];

    function LookUpCodeController ($scope, $state, LookUpCode, User, PrivateCountryCode)
    {
        var vm = this;


        vm.lookUpCodes = [];
        vm.privateCodesList = [];
        vm.users = User.query();
        loadAll();
        vm.user_selection;
        vm.getListOfPrivateCodes = getListOfPrivateCodesFunc;
        vm.dummyVar="start";

        function loadAll()
        {
            LookUpCode.query(function(result)
            {
                //vm.lookUpCodes = result;
            });
        }

        function getAccount()
        {
            Principal.identity().then(function(account) {
                vm.account = account;
                vm.isAuthenticated = Principal.isAuthenticated;
            });
        }

        function getListOfPrivateCodesFunc()
        {
            vm.dummyVar=vm.user_selection;
            vm.privateCodesList = GetPrivateCodesForLogin.getPrivateCodesForLogin(user_selection);
        }
    }
})();
