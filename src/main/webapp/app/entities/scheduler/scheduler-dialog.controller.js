(function() {
    'use strict';

    angular
        .module('fplaApp')
        .controller('SchedulerDialogController', SchedulerDialogController);

    SchedulerDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Scheduler', 'UserAccount'];

    function SchedulerDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Scheduler, UserAccount) {
        var vm = this;

        vm.scheduler = entity;
        vm.clear = clear;
        vm.save = save;
        vm.useraccounts = UserAccount.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.scheduler.id !== null) {
                Scheduler.update(vm.scheduler, onSaveSuccess, onSaveError);
            } else {
                Scheduler.save(vm.scheduler, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('fplaApp:schedulerUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
