(function() {
    'use strict';
    angular
        .module('fplaApp')
        .factory('Scheduler', Scheduler);

    Scheduler.$inject = ['$resource'];

    function Scheduler ($resource) {
        var resourceUrl =  'api/schedulers/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
