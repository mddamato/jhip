(function() {
    'use strict';

    angular
        .module('countryCodeLookupApp')
        .controller('MasterCountryCodeDeleteController',MasterCountryCodeDeleteController);

    MasterCountryCodeDeleteController.$inject = ['$uibModalInstance', 'entity', 'MasterCountryCode'];

    function MasterCountryCodeDeleteController($uibModalInstance, entity, MasterCountryCode) {
        var vm = this;

        vm.masterCountryCode = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            MasterCountryCode.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
