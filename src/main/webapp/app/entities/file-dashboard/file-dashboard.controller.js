(function() {
    'use strict';

    angular
        .module('ebookApp')
        .controller('FileDashboardController', FileDashboardController);

    FileDashboardController.$inject = ['FileDashboard'];

    function FileDashboardController(FileDashboard) {

        var vm = this;

        vm.fileDashboards = [];

        loadAll();

        function loadAll() {
            FileDashboard.query(function(result) {
                vm.fileDashboards = result;
                vm.searchQuery = null;
            });
        }
    }
})();
