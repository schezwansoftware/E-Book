(function() {
    'use strict';

    angular
        .module('ebookApp')
        .controller('UserCertificatesController', UserCertificatesController);

    UserCertificatesController.$inject = ['UserCertificates'];

    function UserCertificatesController(UserCertificates) {

        var vm = this;

        vm.userCertificates = [];

        loadAll();

        function loadAll() {
            UserCertificates.query(function(result) {
                vm.userCertificates = result;
                vm.searchQuery = null;
            });
        }
    }
})();
