(function() {
    'use strict';

    angular
        .module('fplaApp')
        .controller('BrowserController', BrowserController);

    BrowserController.$inject = ['$scope', '$state', 'Browser'];

    function BrowserController ($scope, $state, Browser) {
        var vm = this;

        vm.browsers = [];

        loadAll();

        function loadAll() {
            Browser.query(function(result) {
                vm.browsers = result;
            });
        }
    }
})();
