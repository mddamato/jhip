(function() {
    'use strict';

    angular
        .module('countryCodeLookupApp')
        .controller('LookUpCodeDetailController', LookUpCodeDetailController);

    LookUpCodeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'LookUpCode'];

    function LookUpCodeDetailController($scope, $rootScope, $stateParams, entity, LookUpCode) {
        var vm = this;

        vm.lookUpCode = entity;

        var unsubscribe = $rootScope.$on('countryCodeLookupApp:lookUpCodeUpdate', function(event, result) {
            vm.lookUpCode = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
