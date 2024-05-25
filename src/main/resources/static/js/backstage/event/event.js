function previewImage() {
    if (checkFileSize()) {
        let file = document.getElementById("eventImg").files[0];
        let reader = new FileReader();
        reader.onload = function (e) {
            let img = document.getElementById("imagePreview");
            img.src = e.target.result;
            img.style.display = "block";
        };
        reader.readAsDataURL(file);
    }
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
document.getElementById('eventImg').addEventListener('click', function (event) {
    event.stopPropagation();
});


let map;

// 初始化地圖
function initMap() {
    map = new google.maps.Map(document.getElementById('map'), {
        center: {lat: 24.968305763571706, lng: 121.19443953035086},
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

        geocoder.geocode({'address': city + ' ' + address}, function (results, status) {
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
document.getElementById('eventDetailedAddress').addEventListener('keydown', function (event) {
    if (event.key === 'Enter') {
        event.preventDefault();
        updateMap();
    }
});

// 驗證活動時間
function validateEventTimes() {
    const eventName = document.getElementById('eventName').value;
    const eventCategory = document.getElementById('eventCategory').value;
    const eventCity = document.getElementById('eventCity').value;
    const eventDetailedAddress = document.getElementById('eventDetailedAddress').value;
    const eventAddTime = new Date(document.getElementById('eventAddTime').value);
    const eventRemoveTime = new Date(document.getElementById('eventRemoveTime').value);
    const eventStartTime = new Date(document.getElementById('eventStartTime').value);
    const eventEndTime = new Date(document.getElementById('eventEndTime').value);

    let isValid = true;


    if (!eventName) {
        setInvalid('eventName', '請輸入活動名稱。');
        isValid = false;
    } else {
        setValid('eventName');
    }

    if (!eventCategory) {
        setInvalid('eventCategory', '請選擇活動類別。');
        isValid = false;
    } else {
        setValid('eventCategory');
    }

    if (!eventCity) {
        setInvalid('eventCity', '請輸入活動所在城市。');
        isValid = false;
    } else {
        setValid('eventCity');
    }

    if (!eventDetailedAddress) {
        setInvalid('eventDetailedAddress', '請輸入活動詳細地址。');
        isValid = false;
    } else {
        setValid('eventDetailedAddress');
    }

    if (!eventAddTime){
        setInvalid('eventAddTime', '請選擇活動上架時間。');
        isValid = false;
    } else {
        setValid('eventAddTime');
    }

    if (eventRemoveTime < eventAddTime) {
        setInvalid('eventRemoveTime', '活動下架時間不能比上架時間早。');
        isValid = false;
    } else {
        setValid('eventRemoveTime');
    }

    if (eventEndTime < eventStartTime) {
        setInvalid('eventEndTime', '活動結束時間不能比開始時間早。');
        isValid = false;
    } else {
        setValid('eventEndTime');
    }

    if (eventStartTime < eventAddTime) {
        setInvalid('eventStartTime', '活動開始時間不能比上架時間早。');
        isValid = false;
    } else {
        setValid('eventStartTime');
    }

    return isValid;
}

function setInvalid(id, message) {
    const element = document.getElementById(id);
    element.classList.add('is-invalid');
    element.nextElementSibling.textContent = message;
}

function setValid(id) {
    const element = document.getElementById(id);
    element.classList.remove('is-invalid');
    element.classList.add('is-valid');
    element.nextElementSibling.textContent = '';
}

function checkFileSize() {
    const fileInput = document.getElementById('eventImg');
    const maxSize = 10 * 1024 * 1024; // 10MB

    if (fileInput.files.length > 0) {
        const file = fileInput.files[0];
        if (file.size > maxSize) {
            alert('文件大小超過限制，請上傳小於 10MB 的文件。');
            fileInput.value = ''; // 清空文件輸入
            return false;
        } else {
            return true;
        }
    }
    return false;
}

document.querySelector('form').addEventListener('submit', function (event) {
    if (!validateEventTimes()) {
        event.preventDefault();
    }
});

document.getElementById('eventAddTime').min = new Date().toISOString().slice(0, 16);
document.getElementById('eventStartTime').min = new Date().toISOString().slice(0, 16);
document.getElementById('eventEndTime').min = new Date().toISOString().slice(0, 16);
document.getElementById('eventRemoveTime').min = new Date().toISOString().slice(0, 16);