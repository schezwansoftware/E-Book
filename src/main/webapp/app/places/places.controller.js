(function() {
    'use strict';

    angular
        .module('ebookApp')
        .controller('PlacesController', PlacesController);

    PlacesController.$inject = ['$scope','AlertService','Countries','States','Cities'];

    function PlacesController ($scope,AlertService,Countries,States,Cities) {
        var vm = this;
        var countrycode=null;

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
           States.query({countrycode:vm.country},function (result) {
               vm.states=result;
               countrycode=vm.country;
           });

       };

        vm.stateSelected=function () {
            Cities.query({countrycode:countrycode,state:vm.state},function (result) {
               vm.cities=result;
            });
        }
    }


})();
