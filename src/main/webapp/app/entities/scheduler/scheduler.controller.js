(function() {
    'use strict';

    angular
        .module('fplaApp')
        .controller('SchedulerController', SchedulerController);

    SchedulerController.$inject = ['$scope', '$state', 'Scheduler'];

    function SchedulerController ($scope, $state, Scheduler) {
        var vm = this;
        
        vm.schedulers = [];

        loadAll();

        function loadAll() {
            Scheduler.query(function(result) {
                vm.schedulers = result;
            });
        }
    }
})();
