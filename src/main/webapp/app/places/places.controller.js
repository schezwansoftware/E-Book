(function() {
    'use strict';

    angular
        .module('ebookApp')
        .controller('PlacesController', PlacesController);

    PlacesController.$inject = ['$scope','AlertService','vsGooglePlaceUtility'];

    function PlacesController ($scope,AlertService,vsGooglePlaceUtility) {
        var vm = this;

        vm.submitData=function () {
            AlertService.success("Data submitted");
        };

        vm.myaddress={
            name:'',
            components:{
                city:'',
                state:'',
                postCode:'',
                district:''
            }
        };
    }


})();
