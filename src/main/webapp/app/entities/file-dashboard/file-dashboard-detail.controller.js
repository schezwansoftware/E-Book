(function() {
    'use strict';

    angular
        .module('ebookApp')
        .controller('FileDashboardDetailController', FileDashboardDetailController);

    FileDashboardDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'FileDashboard'];

    function FileDashboardDetailController($scope, $rootScope, $stateParams, previousState, entity, FileDashboard) {
        var vm = this;

        vm.fileDashboard = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('ebookApp:fileDashboardUpdate', function(event, result) {
            vm.fileDashboard = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
