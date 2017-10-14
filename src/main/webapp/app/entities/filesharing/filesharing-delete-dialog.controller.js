(function() {
    'use strict';

    angular
        .module('ebookApp')
        .controller('FilesharingDeleteController',FilesharingDeleteController);

    FilesharingDeleteController.$inject = ['$uibModalInstance', 'entity', 'Filesharing'];

    function FilesharingDeleteController($uibModalInstance, entity, Filesharing) {
        var vm = this;

        vm.filesharing = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Filesharing.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
