(function() {
    'use strict';

    angular
        .module('ebookApp')
        .controller('OrdersController', OrdersController);

    OrdersController.$inject = ['Orders'];

    function OrdersController(Orders) {

        var vm = this;

        vm.orders = [];

        loadAll();

        function loadAll() {
            Orders.query(function(result) {
                vm.orders = result;
                vm.searchQuery = null;
            });
        }
    }
})();
