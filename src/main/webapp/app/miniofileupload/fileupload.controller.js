(function() {
    'use strict';

    angular
        .module('ebookApp')
        .controller('FileUploadController', FileUploadController);

    FileUploadController.$inject = ['$timeout','Upload' ,'$scope', '$stateParams', '$uibModalInstance','AlertService'];

    function FileUploadController ($timeout, Upload,$scope, $stateParams, $uibModalInstance,AlertService) {
              var vm = this;
              vm.clear=clear;
              vm.uploadFile=uploadFile;

         function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function uploadFile(){
        Upload.upload({url:'api/fileuploads',data:{filename:vm.filename,file:vm.file}}).then(function(resp){
        if(resp.data.message==='success'){
        AlertService.success('File Succesfully Uploaded');
        $uibModalInstance.close('success');
        }else
        AlertService.error('Failed to upload!!');
        });
        }
    }
})();
