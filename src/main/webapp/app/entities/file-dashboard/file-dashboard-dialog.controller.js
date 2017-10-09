(function() {
    'use strict';

    angular
        .module('ebookApp')
        .controller('FileDashboardDialogController', FileDashboardDialogController);

    FileDashboardDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'FileDashboard'];

    function FileDashboardDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, FileDashboard) {
        var vm = this;

        vm.fileDashboard = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.fileDashboard.id !== null) {
                FileDashboard.update(vm.fileDashboard, onSaveSuccess, onSaveError);
            } else {
                FileDashboard.save(vm.fileDashboard, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('ebookApp:fileDashboardUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
