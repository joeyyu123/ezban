var loadedMessages = new Set();
$(document).ready(function() {
    var stompClient = null;
    var connected = false;

    $('#toggleChat').on('click', function() {
        var currentUrl = window.location.href;
        fetch('/frontstage/memberchat/checkLoginStatus')
            .then(response => response.json())
            .then(data => {
                if (data.loggedIn) {
                    var eventNo = $('#eventNo').val();
                    if (eventNo) {
                        loadChat(eventNo);
                    }
                } else {
                    window.location.href = '/loginPage?redirect=' + encodeURIComponent(currentUrl);
                }
            })
            .catch(error => {
                console.error('檢查登入狀態時出錯:', error);
                window.location.href = '/loginPage?redirect=' + encodeURIComponent(currentUrl);
            });
    });

    function loadChat(eventNo) {
        $('#chatContainer').show();
        $.ajax({
            url: '/frontstage/memberchat/loadMemberChat/' + eventNo,
            method: 'GET',
            success: function(response) {
                $('#chatContainer').html(response);

                var memberName = $('input[name="memberName"]').val();
                var hostName = $('input[name="hostName"]').val();

                initializeWebSocket(memberName, hostName, eventNo);

                // 加載聊天歷史
                loadChatHistory(memberName, hostName,eventNo);

                // 綁定事件前先解除綁定，確保只綁定一次
                $('#messageInput').off('keypress').on('keypress', handleKeyPress);
                $('#sendButton').off('click').on('click', function() {
                    sendMessage(stompClient, memberName, hostName, eventNo);
                });
            },
            error: function(error) {
                console.error('加載聊天室內容時出錯:', error);
                $('#chatContainer').html('<p>無法加載聊天室內容</p>');
            }
        });
    }

    function loadChatHistory(memberName, hostName, eventNo) {
        $.ajax({
            url: '/frontstage/memberchat/history/' + encodeURIComponent(memberName) + '/' + encodeURIComponent(hostName) + '/' + eventNo,
            method: 'GET',
            success: function(chatHistory) {
                console.log('Loaded chat history:', chatHistory);
                chatHistory.forEach(function(message) {
                    showMessage(message, memberName);
                });
            },
            error: function(jqXHR, textStatus, errorThrown) {
                console.error('Error loading chat history:', {
                    readyState: jqXHR.readyState,
                    responseText: jqXHR.responseText,
                    status: jqXHR.status,
                    statusText: jqXHR.statusText,
                    textStatus: textStatus,
                    errorThrown: errorThrown
                });
            }
        });
    }

    function initializeWebSocket(memberName, hostName, eventNo) {
        if (stompClient !== null && connected) {
            stompClient.disconnect();  // 確保先斷開現有連接
        }
        var socket = new SockJS('/ws');
        stompClient = Stomp.over(socket);

        stompClient.connect({}, function(frame) {
            connected = true;
            $('#status').text('已連線').css('color', 'green');
            console.log('Connected: ' + frame);
            stompClient.subscribe(`/message/event/${eventNo}`, function(message) {
                console.log('Received message: ' + message.body);
                showMessage(JSON.parse(message.body), memberName);
            });
        }, function(error) {
            $('#status').text('未連線').css('color', 'red');
            console.error('Error: ' + error);
            connected = false;
        });
    }

    function sendMessage(stompClient, memberName, hostName, eventNo) {
        var chatInput = $('#messageInput').val();
        if (chatInput) {
            var messageObj = {
                'type':'member',
                'message': chatInput,
                'sender': memberName,
                'receiver': hostName,
                'timestamp': new Date().toISOString(),
                'eventNo': eventNo
            };
            stompClient.send(`/memberchat/sendMessage/${eventNo}`, {}, JSON.stringify(messageObj));
            $('#messageInput').val('');
        }
    }

    function showMessage(message, memberName) {

        // 檢查訊息是否已經顯示過
        var messageKey = message.timestamp + message.sender + message.receiver;
        if (loadedMessages.has(messageKey)) {
            return;
        }

        // 添加到已顯示訊息的集合中
        loadedMessages.add(messageKey);

        var chatHistory = $('#chatHistory');
        var messageElement = $('<div>').addClass('message').text(message.message);

        if (message.sender === memberName) {
            messageElement.addClass('mine');
        } else {
            messageElement.addClass('theirs');
        }

        var timestampElement = $('<span>').addClass('timestamp').text(new Date(message.timestamp).toLocaleString());
        messageElement.append(timestampElement);
        chatHistory.append(messageElement);
        chatHistory.scrollTop(chatHistory[0].scrollHeight);
    }

    function handleKeyPress(event) {
        if (event.which === 13) {
            event.preventDefault();
            $('#sendButton').click();
        }
    }

    // 點擊close
    // document.addEventListener('click', function (e) {
    //      // (document.getElementById('closeButton').style.display = 'none');
    //      if (e.target.id === 'closeButton') {
    //          $('#chatContainer').hide();
    //      }
    // });


});
function closeButton() {
    $('#chatContainer').hide();
}
