(function() {
    'use strict';

    angular
        .module('fplaApp')
        .controller('BrowserDialogController', BrowserDialogController);

    BrowserDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Browser', 'UserAccount'];

    function BrowserDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Browser, UserAccount) {
        var vm = this;

        vm.browser = entity;
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
            if (vm.browser.id !== null) {
                Browser.update(vm.browser, onSaveSuccess, onSaveError);
            } else {
                Browser.save(vm.browser, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('fplaApp:browserUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
