(function() {
    'use strict';

    angular
        .module('fplaApp')
        .controller('ScriptDetailController', ScriptDetailController);

    ScriptDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Script', 'UserAccount'];

    function ScriptDetailController($scope, $rootScope, $stateParams, previousState, entity, Script, UserAccount) {
        var vm = this;

        vm.script = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('fplaApp:scriptUpdate', function(event, result) {
            vm.script = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
