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
// 查看活動評論
function showComments() {
    const eventNo = window.location.href.split('/')[4];
    $.ajax({
        url: '/api/event/comment/' + eventNo,
        type: 'GET',
        dataType: 'json',
        success: function (data) {
            if (data.length === 0) {
                $('#comments').append(`
                <div class="card mb-3">
                    <div class="card-body">
                        <h5 class="card-title">目前沒有評論</h5>
                    </div>
                </div>  
                `)
            }
            data.forEach(function (comment) {
                let stars = '';
                for (let i = 0; i < comment.eventCommentRate; i++) {
                    stars += '<i class="fa-solid fa-star" style="color: #FFD43B;"></i>';
                }
                for (let i = comment.eventCommentRate; i < 5; i++) {
                    stars += '<i class="fa-regular fa-star" style="color: #FFD43B;"></i>';
                }
                $('#comments').append(`
                <div class="card mb-3">
                    <div class="card-body">
                        <h5 class="card-title">${comment.memberNo}</h5>
                        <div class="card-rating">${stars}</div>
                        <p class="card-text">${comment.eventCommentContent}</p>
                    </div>
                </div>  
                `)
            })
        }
    })
}
showComments();