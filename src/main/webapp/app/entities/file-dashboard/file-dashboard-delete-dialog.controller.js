(function() {
    'use strict';

    angular
        .module('ebookApp')
        .controller('FileDashboardDeleteController',FileDashboardDeleteController);

    FileDashboardDeleteController.$inject = ['$uibModalInstance', 'entity', 'FileDashboard'];

    function FileDashboardDeleteController($uibModalInstance, entity, FileDashboard) {
        var vm = this;

        vm.fileDashboard = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            FileDashboard.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
