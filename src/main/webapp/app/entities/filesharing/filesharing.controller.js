(function() {
    'use strict';

    angular
        .module('ebookApp')
        .controller('FilesharingController', FilesharingController);

    FilesharingController.$inject = ['Filesharing'];

    function FilesharingController(Filesharing) {

        var vm = this;

        vm.filesharings = [];

        loadAll();

        function loadAll() {
            Filesharing.query(function(result) {
                vm.filesharings = result;
                vm.searchQuery = null;
            });
        }
    }
})();
