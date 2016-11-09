'use strict';

describe('Controller Tests', function() {

    describe('UserAccount Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockUserAccount, MockScript, MockScheduler;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockUserAccount = jasmine.createSpy('MockUserAccount');
            MockScript = jasmine.createSpy('MockScript');
            MockScheduler = jasmine.createSpy('MockScheduler');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'UserAccount': MockUserAccount,
                'Script': MockScript,
                'Scheduler': MockScheduler
            };
            createController = function() {
                $injector.get('$controller')("UserAccountDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'fplaApp:userAccountUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
