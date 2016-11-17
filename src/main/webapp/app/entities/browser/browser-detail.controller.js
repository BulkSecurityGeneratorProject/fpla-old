(function() {
    'use strict';

    angular
        .module('fplaApp')
        .controller('BrowserDetailController', BrowserDetailController);

    BrowserDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Browser', 'UserAccount'];

    function BrowserDetailController($scope, $rootScope, $stateParams, previousState, entity, Browser, UserAccount) {
        var vm = this;

        vm.browser = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('fplaApp:browserUpdate', function(event, result) {
            vm.browser = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
