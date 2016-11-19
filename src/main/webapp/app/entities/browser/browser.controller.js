(function() {
    'use strict';

    angular
        .module('fplaApp')
        .controller('BrowserController', BrowserController);

    BrowserController.$inject = ['$scope', '$state', 'Browser', 'Script'];

    function BrowserController ($scope, $state, Browser, Script) {
        var vm = this;

        vm.browsers = [];
        vm.scripts = [];
        vm.selectedScripts = [];

        loadAll();

        function loadAll() {
            Browser.query(function(result) {
                vm.browsers = result;
            });
            Script.query(function(result) {
                vm.scripts = result;
            });
        }
    }
})();
