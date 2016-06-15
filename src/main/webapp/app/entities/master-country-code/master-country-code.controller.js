(function() {
    'use strict';

    angular
        .module('countryCodeLookupApp')
        .controller('MasterCountryCodeController', MasterCountryCodeController);

    MasterCountryCodeController.$inject = ['$scope', '$state', 'MasterCountryCode'];

    function MasterCountryCodeController ($scope, $state, MasterCountryCode) {
        var vm = this;

        vm.masterCountryCodes = [];


        loadAll();

        function loadAll() {
            MasterCountryCode.query(function(result) {
                vm.masterCountryCodes = result;
            });
        }
    }
})();
