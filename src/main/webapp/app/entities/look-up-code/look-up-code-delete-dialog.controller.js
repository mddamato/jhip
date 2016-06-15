(function() {
    'use strict';

    angular
        .module('countryCodeLookupApp')
        .controller('LookUpCodeDeleteController',LookUpCodeDeleteController);

    LookUpCodeDeleteController.$inject = ['$uibModalInstance', 'entity', 'LookUpCode'];

    function LookUpCodeDeleteController($uibModalInstance, entity, LookUpCode) {
        var vm = this;

        vm.lookUpCode = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            LookUpCode.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
