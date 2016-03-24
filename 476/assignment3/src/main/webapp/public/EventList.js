
$(document).ready(function () {
    
var map = new GMaps({
  div: '#map',
  zoom: 10,
  lat: 33.8823922,
  lng: -117.8851545
});
var descriptionList = $("input[type=hidden][name=description]").toArray();
var titleList = $("input[type=hidden][name=title]").toArray();
var locationList = $("input[type=hidden][name=location]").toArray();
var dateTimeList = $("input[type=hidden][name=dateTime]").toArray();
    
for ( var i = 0, l = locationList.length; i < l; i++ ) {
      codeAddress($(locationList[i]).val(),
                  $(titleList[i]).val(),
                  $(descriptionList[i]).val(),
                  $(dateTimeList[i]).val());
  } 
  
function codeAddress(address,title,desc,dateTime){
    GMaps.geocode({
        address: address,
        callback: function(results, status) {
          if (status == 'OK') {
            var latlng = results[0].geometry.location;
            map.setCenter(latlng.lat(), latlng.lng());

            map.addMarker({
              title: title,
              description:desc,
              infoWindow: {
                content:" In "+dateTime+ " <br>"+desc
              },
              lat: latlng.lat(),
              lng: latlng.lng(),
              click: function(e) {
              }
            });
          }
        }
    });
}

});
