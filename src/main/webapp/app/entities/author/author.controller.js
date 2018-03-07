(function() {
    'use strict';

    angular
        .module('ebookApp')
        .controller('AuthorController', AuthorController);

    AuthorController.$inject = ['Author'];

    function AuthorController(Author) {

        var vm = this;

        vm.authors = [];

        loadAll();

        function loadAll() {
            Author.query(function(result) {
                vm.authors = result;
                vm.searchQuery = null;
            });
        }
    }
})();
