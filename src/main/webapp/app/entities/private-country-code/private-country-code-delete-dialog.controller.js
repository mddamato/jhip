(function() {
    'use strict';

    angular
        .module('countryCodeLookupApp')
        .controller('PrivateCountryCodeDeleteController',PrivateCountryCodeDeleteController);

    PrivateCountryCodeDeleteController.$inject = ['$uibModalInstance', 'entity', 'PrivateCountryCode'];

    function PrivateCountryCodeDeleteController($uibModalInstance, entity, PrivateCountryCode) {
        var vm = this;

        vm.privateCountryCode = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            PrivateCountryCode.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
