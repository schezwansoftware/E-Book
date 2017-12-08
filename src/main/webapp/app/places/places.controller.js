(function() {
    'use strict';

    angular
        .module('ebookApp')
        .controller('PlacesController', PlacesController);

    PlacesController.$inject = ['$scope','AlertService','Countries','$http','Places'];

    function PlacesController ($scope,AlertService,Countries,$http,Places) {
        var vm = this;

        loadCountries();
       function loadCountries(){
           Countries.query(function (result) {
              vm.countries=result;
           });
        }

        vm.submitData=function () {
            AlertService.success("Data submitted");
        };

       vm.countrySelected=function () {

       }
    }


})();
