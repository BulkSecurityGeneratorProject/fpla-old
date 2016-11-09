(function() {
    'use strict';

    angular
        .module('fplaApp')
        .controller('UserAccountDialogController', UserAccountDialogController);

    UserAccountDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'UserAccount', 'Script', 'Scheduler'];

    function UserAccountDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, UserAccount, Script, Scheduler) {
        var vm = this;

        vm.userAccount = entity;
        vm.clear = clear;
        vm.save = save;
        vm.scripts = Script.query();
        vm.schedulers = Scheduler.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.userAccount.id !== null) {
                UserAccount.update(vm.userAccount, onSaveSuccess, onSaveError);
            } else {
                UserAccount.save(vm.userAccount, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('fplaApp:userAccountUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
