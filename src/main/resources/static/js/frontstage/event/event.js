// Initialize and add the map
let map;

async function initMap() {
    let address = document.getElementById('eventAddress').textContent;
    let LatLng = null;

    const {Map} = await google.maps.importLibrary("maps");
    const {AdvancedMarkerElement} = await google.maps.importLibrary("marker");

    map = new Map(document.getElementById('map'), {
        center: {lat: 24.968305763571706, lng: 121.19443953035086},
        zoom: 15,
        mapId: "DEMO_MAP_ID",
    });

    if (address) {
        let geocoder = new google.maps.Geocoder();

        geocoder.geocode({ 'address':  address }, function(results, status) {
            if (status === 'OK') {
                LatLng = new google.maps.LatLng(results[0].geometry.location.lat(), results[0].geometry.location.lng());
                let marker = new AdvancedMarkerElement({
                    position: LatLng,
                    map: map
                });
                map.setCenter(results[0].geometry.location);
            } else {
                alert('Geocode was not successful for the following reason: ' + status);
            }
        });
    }


}
// function initMap() {