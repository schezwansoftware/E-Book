(function() {
    'use strict';

    angular
        .module('ebookApp')
        .controller('UserCertificatesDetailController', UserCertificatesDetailController);

    UserCertificatesDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'UserCertificates'];

    function UserCertificatesDetailController($scope, $rootScope, $stateParams, previousState, entity, UserCertificates) {
        var vm = this;

        vm.userCertificates = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('ebookApp:userCertificatesUpdate', function(event, result) {
            vm.userCertificates = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
