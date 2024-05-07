
    function previewImage() {
        let file = document.getElementById("eventImg").files[0];
        let reader = new FileReader();
        reader.onload = function(e) {
            let img = document.getElementById("imagePreview");
            img.src = e.target.result;
            img.style.display = "block";
        };
        reader.readAsDataURL(file);
    }

    function dragOverHandler(ev) {
        ev.preventDefault();
    }

    function dropHandler(ev) {
        ev.preventDefault();

        if (ev.dataTransfer.items) {
            for (let i = 0; i < ev.dataTransfer.items.length; i++) {
                if (ev.dataTransfer.items[i].kind === 'file') {
                    let file = ev.dataTransfer.items[i].getAsFile();
                    let dt = new DataTransfer();
                    dt.items.add(file);
                    document.getElementById('eventImg').files = dt.files;
                    previewImage();
                }
            }
        } else {
            for (let i = 0; i < ev.dataTransfer.files.length; i++) {
                console.log('... file[' + i + '].name = ' + ev.dataTransfer.files[i].name);
            }
        }
    }
    document.getElementById('eventImg').addEventListener('change', previewImage);
    document.getElementById('eventImg').addEventListener('click', function(event) {
        event.stopPropagation();
    });


    let map;

    // 初始化地圖
    function initMap() {
        map = new google.maps.Map(document.getElementById('map'), {
            center: { lat: 24.968305763571706, lng: 121.19443953035086 },
            zoom: 17
        });

        // 在地圖初始化後更新地圖
        updateMap();
    }

    // 更新地圖
    function updateMap() {
        let city = document.getElementById('eventCity').value;
        let address = document.getElementById('eventDetailedAddress').value;
        let LatLng = null;

        if (city && address) {
            let geocoder = new google.maps.Geocoder();

            geocoder.geocode({ 'address': city + ' ' + address }, function(results, status) {
                if (status === 'OK') {
                    LatLng = new google.maps.LatLng(results[0].geometry.location.lat(), results[0].geometry.location.lng());
                    let marker = new google.maps.Marker({
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

    // 當用戶輸入完活動所在城市和活動地址後，更新地圖
    // document.getElementById('eventCity').addEventListener('change', updateMap);
    document.getElementById('eventDetailedAddress').addEventListener('blur', updateMap);
    document.getElementById('eventDetailedAddress').addEventListener('keydown', function(event) {
        if (event.key === 'Enter') {
            event.preventDefault();
            updateMap();
        }
    });

    // // 格式化日期時間
    // window.onload = function() {
    //     let form = document.querySelector('form');
    //     form.onsubmit = function(e) {
    //         console.log(e);
    //         e.preventDefault();
    //         let dateTimeFields = ['eventAddTime', 'eventRemoveTime', 'eventStartTime', 'eventEndTime', 'registrationStartTime', 'registrationEndTime'];
    //         dateTimeFields.forEach(function(fieldId) {
    //             let field = document.getElementById(fieldId);
    //             if (field) {
    //                 let newValue = field.value.replace("T", " ");
    //                 field.setAttribute("value", newValue);
    //                 field.value = newValue;
    //                 alert(field.value);
    //             }
    //         });
    //         // form.submit();
    //     };
    // };
