(function() {
    'use strict';

    angular
        .module('ebookApp')
        .controller('PlacesController', PlacesController);

    PlacesController.$inject = ['$scope','AlertService'];

    function PlacesController ($scope,AlertService) {
        var vm = this;

        vm.submitData=function () {
            AlertService.success("Data submitted");
        }

    }
})();
