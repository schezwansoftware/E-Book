(function() {
    'use strict';

    angular
        .module('ebookApp')
        .controller('OrdersDetailController', OrdersDetailController);

    OrdersDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Orders'];

    function OrdersDetailController($scope, $rootScope, $stateParams, previousState, entity, Orders) {
        var vm = this;

        vm.orders = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('ebookApp:ordersUpdate', function(event, result) {
            vm.orders = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
