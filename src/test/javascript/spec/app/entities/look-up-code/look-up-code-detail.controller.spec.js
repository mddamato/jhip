'use strict';

describe('Controller Tests', function() {

    describe('LookUpCode Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockLookUpCode;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockLookUpCode = jasmine.createSpy('MockLookUpCode');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'LookUpCode': MockLookUpCode
            };
            createController = function() {
                $injector.get('$controller')("LookUpCodeDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'countryCodeLookupApp:lookUpCodeUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
