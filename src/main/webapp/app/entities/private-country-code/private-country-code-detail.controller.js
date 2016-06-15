(function() {
    'use strict';

    angular
        .module('countryCodeLookupApp')
        .controller('PrivateCountryCodeDetailController', PrivateCountryCodeDetailController);

    PrivateCountryCodeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'PrivateCountryCode', 'MasterCountryCode', 'User'];

    function PrivateCountryCodeDetailController($scope, $rootScope, $stateParams, entity, PrivateCountryCode, MasterCountryCode, User) {
        var vm = this;

        vm.privateCountryCode = entity;

        var unsubscribe = $rootScope.$on('countryCodeLookupApp:privateCountryCodeUpdate', function(event, result) {
            vm.privateCountryCode = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
