(function() {
    'use strict';

    angular
        .module('fplaApp')
        .controller('ScriptController', ScriptController);

    ScriptController.$inject = ['$scope', '$state', 'Script'];

    function ScriptController ($scope, $state, Script) {
        var vm = this;
        
        vm.scripts = [];

        loadAll();

        function loadAll() {
            Script.query(function(result) {
                vm.scripts = result;
            });
        }
    }
})();
