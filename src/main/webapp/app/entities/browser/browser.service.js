(function() {
    'use strict';
    angular
        .module('fplaApp')
        .factory('Browser', Browser);

    Browser.$inject = ['$resource'];

    function Browser ($resource) {
        var resourceUrl =  'api/browsers/:id';

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
