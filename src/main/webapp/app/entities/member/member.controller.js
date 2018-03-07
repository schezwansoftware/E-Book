(function() {
    'use strict';

    angular
        .module('ebookApp')
        .controller('MemberController', MemberController);

    MemberController.$inject = ['Member'];

    function MemberController(Member) {

        var vm = this;

        vm.members = [];

        loadAll();

        function loadAll() {
            Member.query(function(result) {
                vm.members = result;
                vm.searchQuery = null;
            });
        }
    }
})();
