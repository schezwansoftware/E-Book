(function() {
    'use strict';

    angular
        .module('ebookApp')
        .controller('FilesharingDialogController', FilesharingDialogController);

    FilesharingDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Filesharing'];

    function FilesharingDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Filesharing) {
        var vm = this;

        vm.filesharing = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.filesharing.id !== null) {
                Filesharing.update(vm.filesharing, onSaveSuccess, onSaveError);
            } else {
                Filesharing.save(vm.filesharing, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('ebookApp:filesharingUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.sharedon = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
