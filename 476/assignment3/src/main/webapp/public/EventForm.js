
$(document).ready(function () {

    //$('#eventDateTime').attr("min",new Date($.now()));
    
    //alert($('#eventDateTime').attr("min"));
    
    
});

var map = new GMaps({
  div: '#map',
  lat: 33.8823922,
  lng: -117.8851545
});
function checkGeocode() {
    GMaps.geocode({
        address: $('#location').val(),
        callback: function(results, status) {
          if (status == 'OK') {
            var latlng = results[0].geometry.location;
            map.setCenter(latlng.lat(), latlng.lng());
            map.addMarker({
              lat: latlng.lat(),
              lng: latlng.lng()
            });
          }
        }
      });
}
