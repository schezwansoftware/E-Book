(function() {
    'use strict';

    angular
        .module('ebookApp')
        .controller('UserCertificatesDeleteController',UserCertificatesDeleteController);

    UserCertificatesDeleteController.$inject = ['$uibModalInstance', 'entity', 'UserCertificates'];

    function UserCertificatesDeleteController($uibModalInstance, entity, UserCertificates) {
        var vm = this;

        vm.userCertificates = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            UserCertificates.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
