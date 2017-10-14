(function() {
    'use strict';

    angular
        .module('ebookApp')
        .controller('FilesharingDetailController', FilesharingDetailController);

    FilesharingDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Filesharing'];

    function FilesharingDetailController($scope, $rootScope, $stateParams, previousState, entity, Filesharing) {
        var vm = this;

        vm.filesharing = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('ebookApp:filesharingUpdate', function(event, result) {
            vm.filesharing = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
