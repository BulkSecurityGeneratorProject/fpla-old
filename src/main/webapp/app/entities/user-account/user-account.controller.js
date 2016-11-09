(function() {
    'use strict';

    angular
        .module('fplaApp')
        .controller('UserAccountController', UserAccountController);

    UserAccountController.$inject = ['$scope', '$state', 'UserAccount'];

    function UserAccountController ($scope, $state, UserAccount) {
        var vm = this;
        
        vm.userAccounts = [];

        loadAll();

        function loadAll() {
            UserAccount.query(function(result) {
                vm.userAccounts = result;
            });
        }
    }
})();
