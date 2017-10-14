(function () {
    'use strict';

    angular
        .module('ebookApp')
        .controller('FiledashboardController', FiledashboardController);

    FiledashboardController.$inject = ['$scope', '$window', 'Principal', '$state', 'FileDashboard', '$http'];

    function FiledashboardController($scope, $window, Principal, $state, FileDashboard, $http) {
        var vm = this;
        vm.downloadFile = downloadFile;
        vm.viewFile=viewFile;
        var fileName;

        FileDashboard.query(function (result) {
            vm.uploadedFiles = result;
        });

        function downloadFile(filename) {
            fileName = filename;
            $http.get('/api/downloadfile?filename=' + filename).then(onSuccess, onError);
        }

        function viewFile(filename) {
            fileName = filename;
            $http.get('/api/viewfile?filename=' + filename).then(onViewSuccess, onError);
        }


        function onSuccess() {
            console.log('success');
            $window.location = '/api/downloadfile?filename=' + fileName;
        }

        function onViewSuccess() {
            console.log('success');
            $window.location = '/api/viewfile?filename=' + fileName;
        }

        function onError() {
            console.log('error');
        }

    }
})();
