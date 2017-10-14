(function() {
    'use strict';

    angular
        .module('ebookApp')
        .controller('FileUploadController', FileUploadController);

    FileUploadController.$inject = ['$timeout','Upload' ,'$scope', '$state', '$uibModalInstance','AlertService','FileDashboard','Principal'];

    function FileUploadController ($timeout, Upload,$scope, $state, $uibModalInstance,AlertService,FileDashboard,Principal) {
              var vm = this;
              vm.clear=clear;
              vm.uploadFile=uploadFile;
              vm.dashboardObject={};

         function clear () {
            $uibModalInstance.dismiss('cancel');
        }
        Principal.identity().then(function(result){
        vm.dashboardObject.createdby=result.login;
        });

        function uploadFile(){
        Upload.upload({url:'api/fileuploads',data:{filename:vm.filename,file:vm.file}}).then(function(resp){
        if(resp.data.message==='success'){
        AlertService.success('File Succesfully Uploaded');
        vm.dashboardObject.filename=vm.filename;
        vm.dashboardObject.filesize=vm.file.size;
        FileDashboard.save(vm.dashboardObject,onsavesuccess,onsaveerror)
        $uibModalInstance.close('success');
        $state.go('filedashboard', {}, {reload: true});
        }else
        AlertService.error('Failed to upload!!');
        });
        }

        function onsavesuccess(){
        console.log('success');
        }
        function onsaveerror(){
        console.log('error');
        }
    }
})();
