(function() {
    'use strict';

    angular
        .module('fplaApp')
        .controller('UserAccountDeleteController',UserAccountDeleteController);

    UserAccountDeleteController.$inject = ['$uibModalInstance', 'entity', 'UserAccount'];

    function UserAccountDeleteController($uibModalInstance, entity, UserAccount) {
        var vm = this;

        vm.userAccount = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            UserAccount.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
