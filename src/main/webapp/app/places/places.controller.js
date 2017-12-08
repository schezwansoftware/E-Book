(function() {
    'use strict';

    angular
        .module('ebookApp')
        .controller('PlacesController', PlacesController);

    PlacesController.$inject = ['$scope','AlertService','Country','$http','Places'];

    function PlacesController ($scope,AlertService,Country,$http,Places) {
        var vm = this;

        loadCountries();
       function loadCountries(){
           Country.query(function (result) {
              vm.countries=result;
           });
        }
        vm.submitData=function () {
            AlertService.success("Data submitted");
        };

       vm.countrySelected=function () {
           Places.query({countrycode:vm.country,postalcode:vm.postalcode},function (result) {
              console.log(result) ;
           });
       }
    }


})();
