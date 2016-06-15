(function() {
    'use strict';

    angular
        .module('countryCodeLookupApp')
        .controller('PrivateCountryCodeDialogController', PrivateCountryCodeDialogController);

    PrivateCountryCodeDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'PrivateCountryCode', 'MasterCountryCode', 'User'];

    function PrivateCountryCodeDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, PrivateCountryCode, MasterCountryCode, User) {
        var vm = this;

        vm.privateCountryCode = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.mastercountrycodes = MasterCountryCode.query();
        vm.users = User.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.privateCountryCode.id !== null) {
                PrivateCountryCode.update(vm.privateCountryCode, onSaveSuccess, onSaveError);
            } else {
                PrivateCountryCode.save(vm.privateCountryCode, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('countryCodeLookupApp:privateCountryCodeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.startDate = false;
        vm.datePickerOpenStatus.endDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
