(function() {
    'use strict';

    angular
        .module('fplaApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('browser', {
            parent: 'entity',
            url: '/browser',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'fplaApp.browser.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/browser/browsers.html',
                    controller: 'BrowserController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('browser');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('browser-detail', {
            parent: 'entity',
            url: '/browser/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'fplaApp.browser.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/browser/browser-detail.html',
                    controller: 'BrowserDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('browser');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Browser', function($stateParams, Browser) {
                    return Browser.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'browser',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('browser-detail.edit', {
            parent: 'browser-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/browser/browser-dialog.html',
                    controller: 'BrowserDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Browser', function(Browser) {
                            return Browser.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('browser.new', {
            parent: 'browser',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', 'Browser', function($stateParams, $state, Browser) {
                Browser.save({});
                $state.go('browser', null, { reload: 'browser'});
            }]
        })
        .state('browser.edit', {
            parent: 'browser',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/browser/browser-dialog.html',
                    controller: 'BrowserDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Browser', function(Browser) {
                            return Browser.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('browser', null, { reload: 'browser' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('browser.delete', {
            parent: 'browser',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/browser/browser-delete-dialog.html',
                    controller: 'BrowserDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Browser', function(Browser) {
                            return Browser.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('browser', null, { reload: 'browser' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('browser.run', {
            parent: 'browser',
            url: '/{browserId}/run/{scriptId}',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', 'Browser', function($stateParams, $state, Browser) {
                Browser.run({ browserId : $stateParams.browserId, scriptId : $stateParams.scriptId});
                $state.go('browser', null, { reload: 'browser'});
            }]
        });
    }

})();
