(function() {
    'use strict';

    angular
        .module('countryCodeLookupApp')
        .controller('MasterCountryCodeDetailController', MasterCountryCodeDetailController);

    MasterCountryCodeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'MasterCountryCode'];

    function MasterCountryCodeDetailController($scope, $rootScope, $stateParams, entity, MasterCountryCode) {
        var vm = this;

        vm.masterCountryCode = entity;

        var unsubscribe = $rootScope.$on('countryCodeLookupApp:masterCountryCodeUpdate', function(event, result) {
            vm.masterCountryCode = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
