(function() {
    'use strict';

    angular
        .module('fplaApp')
        .controller('SchedulerDeleteController',SchedulerDeleteController);

    SchedulerDeleteController.$inject = ['$uibModalInstance', 'entity', 'Scheduler'];

    function SchedulerDeleteController($uibModalInstance, entity, Scheduler) {
        var vm = this;

        vm.scheduler = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Scheduler.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
