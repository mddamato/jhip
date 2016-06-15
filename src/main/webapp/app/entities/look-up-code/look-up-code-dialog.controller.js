(function() {
    'use strict';

    angular
        .module('countryCodeLookupApp')
        .controller('LookUpCodeDialogController', LookUpCodeDialogController);

    LookUpCodeDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'LookUpCode'];

    function LookUpCodeDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, LookUpCode) {
        var vm = this;

        vm.lookUpCode = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.lookUpCode.id !== null) {
                LookUpCode.update(vm.lookUpCode, onSaveSuccess, onSaveError);
            } else {
                LookUpCode.save(vm.lookUpCode, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('countryCodeLookupApp:lookUpCodeUpdate', result);
            //$uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
