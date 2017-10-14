(function() {
    'use strict';

    angular
        .module('ebookApp')
        .controller('FilesharedController', FilesharedController);

    FilesharedController.$inject = ['$uibModalInstance','AlertService','Principal','Filesharing'];

    function FilesharedController ($uibModalInstance,AlertService,Principal,Filesharing) {
        var vm = this;
        vm.clear=clear;
        var filesharing={
            filename: null,
            sharedby: null,
            sharedto: null,
            sharedon: null,
            verified: null,
            id: null
        };

        function clear() {
            $uibModalInstance.close();
        }

        Principal.identity().then(function (account) {
        filesharing.sharedby=account.login;
        filesharing.sharedon=Date.now();
        });

        vm.shareFile=function () {
            filesharing.filename=vm.filename;
            filesharing.sharedto=vm.sharedto;
            Filesharing.save(filesharing,onSuccess,onError);
        }

        function onSuccess() {
            console.log('success');
        }

        function onError() {
            console.log('error');
        }


    }
})();
