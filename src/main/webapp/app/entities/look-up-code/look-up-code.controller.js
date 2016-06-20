(function()
{
    'use strict';

    angular
        .module('countryCodeLookupApp')
        .controller('LookUpCodeController', LookUpCodeController);

    LookUpCodeController.$inject = ['$scope','$state','User','PrivateCountryCode'];

    function LookUpCodeController ($scope,$state,User,PrivateCountryCode)
    {
        var vm = this;

        vm.lookUpCodes = [];
        vm.privateCodesList = [];
        vm.users = User.query();
        vm.user_selection;
        vm.getListOfPrivateCodes = getListOfPrivateCodesFunc;
        vm.dummyVar="start";

        function getListOfPrivateCodesFunc()
        {
            vm.dummyVar=vm.user_selection;
            vm.privateCodesList = GetPrivateCodesForLogin.getPrivateCodesForLogin(user_selection);
        }
    }
})();
