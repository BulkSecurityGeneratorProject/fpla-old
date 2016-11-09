(function() {
    'use strict';

    angular
        .module('fplaApp')
        .controller('UserAccountDetailController', UserAccountDetailController);

    UserAccountDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'UserAccount', 'Script', 'Scheduler'];

    function UserAccountDetailController($scope, $rootScope, $stateParams, previousState, entity, UserAccount, Script, Scheduler) {
        var vm = this;

        vm.userAccount = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('fplaApp:userAccountUpdate', function(event, result) {
            vm.userAccount = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
