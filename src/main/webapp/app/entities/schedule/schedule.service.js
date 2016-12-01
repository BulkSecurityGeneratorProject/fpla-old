(function() {
    'use strict';
    angular
        .module('fplaApp')
        .factory('Schedule', Schedule);

    Schedule.$inject = ['$resource'];

    function Schedule ($resource) {
        var resourceUrl =  'api/schedules/:id';

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
