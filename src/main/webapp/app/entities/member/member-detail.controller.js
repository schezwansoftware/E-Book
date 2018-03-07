(function() {
    'use strict';

    angular
        .module('ebookApp')
        .controller('MemberDetailController', MemberDetailController);

    MemberDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Member'];

    function MemberDetailController($scope, $rootScope, $stateParams, previousState, entity, Member) {
        var vm = this;

        vm.member = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('ebookApp:memberUpdate', function(event, result) {
            vm.member = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
