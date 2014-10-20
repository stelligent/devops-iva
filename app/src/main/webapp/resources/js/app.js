angular.module('devops', ['ngRoute'])

.service('Api', function($http, $q) {
    'use strict';

    this.allParticipants = function() {
        var deferred = $q.defer();
        $http.get('api/participants')
            .success(function(response) {
                deferred.resolve(response);
            })
            .error(function(response) {
                deferred.reject(response);
            });
        return deferred.promise;
    }
})

.controller('AppCtrl', function($interval, Api) {
    'use strict';
    var ctrl = this;

    $interval(function() {
        Api.allParticipants().then(function(participants) {
            ctrl.participantRows = _.groupBy(_.sortBy(participants, 'name'), function(p, idx) {
                return Math.floor(idx / 4);
            });
        })
    }, 2000);
});