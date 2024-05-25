function checkLoginStatus() {
    return fetch('/frontstage/memberchat/checkLoginStatus')
        .then(response => response.json())
        .then(data => data.loggedIn)
        .catch(error => {
            console.error('檢查登入狀態時出錯:', error);
            return false;
        });
}


function initFavorites() {
    const memberNo = document.getElementById('body').getAttribute('data-member-no');
    fetch(`/saveevent/findFavoritesByMember/${memberNo}`)
        .then(response => response.json())
        .then(data => {
            data.forEach(event => {
                const icon = document.querySelector(`a[data-event-no="${event.eventNo}"] i`);
                if (icon) {
                    icon.classList.add('favorite');
                    icon.style.color = 'red';
                }
            });
        })
        .catch(error => {
            console.error('初始化收藏狀態時出錯:', error);
        });
}

function toggleFavorite(element, e) {
    e.preventDefault();

    checkLoginStatus().then(isLoggedIn => {
        if (!isLoggedIn) {
            const currentUrl = window.location.href;
            window.location.href = '/loginPage?redirect=' + encodeURIComponent(currentUrl);
            return;
        }

        const icon = element.querySelector('i');
        const isFavorite = icon.classList.contains('favorite');
        const eventNoStr = element.getAttribute('data-event-no');
        const eventNo = parseInt(eventNoStr, 10);

        if (isNaN(eventNo)) {
            console.error('活動編號轉換失敗，值為:', eventNoStr);
            return;
        }

        const newStatus = isFavorite ? 0 : 1;
        const memberNo = document.getElementById('body').getAttribute('data-member-no');

        const data = {
            eventNo: eventNo,
            memberNo: parseInt(memberNo, 10)
        };

        fetch('/saveevent/insert', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: new URLSearchParams(data).toString()
        })
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    if (newStatus === 1) {
                        icon.classList.add('favorite');
                        icon.style.color = 'red';
                        Swal.fire({
                            title: '已收藏',
                            text: '你已收藏該活動。',
                            icon: 'success',
                            confirmButtonText: '確定'
                        });
                    } else {
                        icon.classList.remove('favorite');
                        icon.style.color = '';
                        Swal.fire({
                            title: '取消收藏',
                            text: '你已取消收藏該活動。',
                            icon: 'warning',
                            confirmButtonText: '確定'
                        });
                    }
                } else {
                    console.error("收藏操作失敗");
                }
            })
            .catch(error => {
                console.error('錯誤:', error);
            });
    });
}

