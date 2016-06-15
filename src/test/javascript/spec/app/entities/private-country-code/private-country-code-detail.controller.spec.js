'use strict';

describe('Controller Tests', function() {

    describe('PrivateCountryCode Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPrivateCountryCode, MockMasterCountryCode, MockUser;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPrivateCountryCode = jasmine.createSpy('MockPrivateCountryCode');
            MockMasterCountryCode = jasmine.createSpy('MockMasterCountryCode');
            MockUser = jasmine.createSpy('MockUser');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'PrivateCountryCode': MockPrivateCountryCode,
                'MasterCountryCode': MockMasterCountryCode,
                'User': MockUser
            };
            createController = function() {
                $injector.get('$controller')("PrivateCountryCodeDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'countryCodeLookupApp:privateCountryCodeUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
