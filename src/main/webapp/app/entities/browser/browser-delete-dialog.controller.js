(function() {
    'use strict';

    angular
        .module('fplaApp')
        .controller('BrowserDeleteController',BrowserDeleteController);

    BrowserDeleteController.$inject = ['$uibModalInstance', 'entity', 'Browser'];

    function BrowserDeleteController($uibModalInstance, entity, Browser) {
        var vm = this;

        vm.browser = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Browser.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
