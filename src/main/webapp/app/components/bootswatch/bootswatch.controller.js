(function() {
    'use strict';

    angular
        .module('ebookApp')
        .controller('BootswatchController', BootswatchController);

    BootswatchController.$inject = ['$scope','BootSwatchService'];

    function BootswatchController ($scope,BootSwatchService) {
        var vm = this;

        BootSwatchService.get(function (themes) {
            vm.themes = themes.themes;
        });


    }
})();
