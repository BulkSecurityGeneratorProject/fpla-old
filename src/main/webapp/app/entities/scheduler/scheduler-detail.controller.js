(function() {
    'use strict';

    angular
        .module('fplaApp')
        .controller('SchedulerDetailController', SchedulerDetailController);

    SchedulerDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Scheduler', 'UserAccount'];

    function SchedulerDetailController($scope, $rootScope, $stateParams, previousState, entity, Scheduler, UserAccount) {
        var vm = this;

        vm.scheduler = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('fplaApp:schedulerUpdate', function(event, result) {
            vm.scheduler = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
