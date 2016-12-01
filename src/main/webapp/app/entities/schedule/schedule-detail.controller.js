(function() {
    'use strict';

    angular
        .module('fplaApp')
        .controller('ScheduleDetailController', ScheduleDetailController);

    ScheduleDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Schedule'];

    function ScheduleDetailController($scope, $rootScope, $stateParams, previousState, entity, Schedule) {
        var vm = this;

        vm.schedule = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('fplaApp:scheduleUpdate', function(event, result) {
            vm.schedule = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
