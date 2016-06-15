'use strict';

describe('Controller Tests', function() {

    describe('MasterCountryCode Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockMasterCountryCode;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockMasterCountryCode = jasmine.createSpy('MockMasterCountryCode');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'MasterCountryCode': MockMasterCountryCode
            };
            createController = function() {
                $injector.get('$controller')("MasterCountryCodeDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'countryCodeLookupApp:masterCountryCodeUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
