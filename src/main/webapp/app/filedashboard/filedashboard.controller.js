(function() {
    'use strict';

    angular
        .module('ebookApp')
        .controller('FiledashboardController', FiledashboardController);

    FiledashboardController.$inject = ['$scope', 'Principal', 'LoginService', '$state'];

    function FiledashboardController ($scope, Principal, LoginService, $state) {
        var vm = this;


    }
})();
