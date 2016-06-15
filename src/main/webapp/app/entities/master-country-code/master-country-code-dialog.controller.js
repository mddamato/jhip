(function() {
    'use strict';

    angular
        .module('countryCodeLookupApp')
        .controller('MasterCountryCodeDialogController', MasterCountryCodeDialogController);

    MasterCountryCodeDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'MasterCountryCode'];

    function MasterCountryCodeDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, MasterCountryCode) {
        var vm = this;

        vm.masterCountryCode = entity;
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
            if (vm.masterCountryCode.id !== null) {
                MasterCountryCode.update(vm.masterCountryCode, onSaveSuccess, onSaveError);
            } else {
                MasterCountryCode.save(vm.masterCountryCode, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('countryCodeLookupApp:masterCountryCodeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
