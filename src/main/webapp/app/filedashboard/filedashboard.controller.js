(function() {
    'use strict';

    angular
        .module('ebookApp')
        .controller('FiledashboardController', FiledashboardController);

    FiledashboardController.$inject = ['$scope', 'Principal' , '$state','FileDashboard'];

    function FiledashboardController ($scope, Principal, $state,FileDashboard) {
        var vm = this;

        FileDashboard.query(function(result){
        vm.uploadedFiles=result;
        });

    }
})();
