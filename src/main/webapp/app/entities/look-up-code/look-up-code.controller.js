(function()
{
    'use strict';

    angular
        .module('countryCodeLookupApp')
        .controller('LookUpCodeController', LookUpCodeController);

    LookUpCodeController.$inject = ['$scope','$state','User','PrivateCountryCode','GetPrivateCodesForLogin'];

    function LookUpCodeController ($scope,$state,User,PrivateCountryCode,GetPrivateCodesForLogin)
    {
        var vm = this;

        vm.lookUpCodes = [];
        vm.privateCodesList = [];
        vm.users = User.query();
        vm.user_selection;
        vm.getListOfPrivateCodes = getListOfPrivateCodesFunc;
        vm.dummyVar="start";
        vm.user_selection;

        function getListOfPrivateCodesFunc()
        {
            vm.dummyVar=vm.user_selection;
            vm.privateCodesList = GetPrivateCodesForLogin.getPrivateCodesForLogin(vm.user_selection);
        }
    }
})();
