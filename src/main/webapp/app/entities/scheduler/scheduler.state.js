(function() {
    'use strict';

    angular
        .module('fplaApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('scheduler', {
            parent: 'entity',
            url: '/scheduler',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'fplaApp.scheduler.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/scheduler/schedulers.html',
                    controller: 'SchedulerController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('scheduler');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('scheduler-detail', {
            parent: 'entity',
            url: '/scheduler/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'fplaApp.scheduler.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/scheduler/scheduler-detail.html',
                    controller: 'SchedulerDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('scheduler');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Scheduler', function($stateParams, Scheduler) {
                    return Scheduler.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'scheduler',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('scheduler-detail.edit', {
            parent: 'scheduler-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/scheduler/scheduler-dialog.html',
                    controller: 'SchedulerDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Scheduler', function(Scheduler) {
                            return Scheduler.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('scheduler.new', {
            parent: 'scheduler',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/scheduler/scheduler-dialog.html',
                    controller: 'SchedulerDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                running: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('scheduler', null, { reload: 'scheduler' });
                }, function() {
                    $state.go('scheduler');
                });
            }]
        })
        .state('scheduler.edit', {
            parent: 'scheduler',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/scheduler/scheduler-dialog.html',
                    controller: 'SchedulerDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Scheduler', function(Scheduler) {
                            return Scheduler.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('scheduler', null, { reload: 'scheduler' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('scheduler.delete', {
            parent: 'scheduler',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/scheduler/scheduler-delete-dialog.html',
                    controller: 'SchedulerDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Scheduler', function(Scheduler) {
                            return Scheduler.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('scheduler', null, { reload: 'scheduler' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
