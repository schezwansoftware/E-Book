(function() {
    'use strict';

    angular
        .module('ebookApp')
        .controller('UserCertificatesDialogController', UserCertificatesDialogController);

    UserCertificatesDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'UserCertificates'];

    function UserCertificatesDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, UserCertificates) {
        var vm = this;

        vm.userCertificates = entity;
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
            if (vm.userCertificates.id !== null) {
                UserCertificates.update(vm.userCertificates, onSaveSuccess, onSaveError);
            } else {
                UserCertificates.save(vm.userCertificates, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('ebookApp:userCertificatesUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
