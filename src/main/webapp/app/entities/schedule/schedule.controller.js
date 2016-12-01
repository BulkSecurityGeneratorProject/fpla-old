(function() {
    'use strict';

    angular
        .module('fplaApp')
        .controller('ScheduleController', ScheduleController);

    ScheduleController.$inject = ['$scope', '$state', 'Schedule'];

    function ScheduleController ($scope, $state, Schedule) {
        var vm = this;
        
        vm.schedules = [];

        loadAll();

        function loadAll() {
            Schedule.query(function(result) {
                vm.schedules = result;
            });
        }
    }
})();
