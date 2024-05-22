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
                loadChatHistory(memberName, hostName);

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

    function loadChatHistory(memberName, hostName) {
        $.ajax({
            url: '/frontstage/memberchat/history/' + memberName + '/' + hostName,
            method: 'GET',
            success: function(chatHistory) {
                console.log('Loaded chat history:', chatHistory);
                chatHistory.forEach(function(message) {
                    showMessage(message, memberName);
                });
            },
            error: function(error) {
                console.error('Error loading chat history:', error);
            }
        });
    }

    function initializeWebSocket(memberName, hostName, eventNo) {
        if (connected && stompClient !== null) {
            return; // 如果已經連接，則不再初始化
        }
        var socket = new SockJS('/ws');
        stompClient = Stomp.over(socket);

        stompClient.connect({}, function(frame) {
            connected = true;
            $('#status').text('已連線').css('color', 'green');
            stompClient.subscribe(`/message/event/${eventNo}`, function(message) {
                showMessage(JSON.parse(message.body), memberName);
            });
        }, function(error) {
            $('#status').text('未連線').css('color', 'red');
            connected = false;
        });
    }

    function sendMessage(stompClient, memberName, hostName, eventNo) {
        var chatInput = $('#messageInput').val();
        if (chatInput) {
            var messageObj = {
                'message': chatInput,
                'sender': memberName,
                'receiver': hostName,
                'timestamp': new Date().toISOString()
            };
            stompClient.send(`/memberchat/sendMessage/${eventNo}`, {}, JSON.stringify(messageObj));
            $('#messageInput').val('');
        }
    }

    function showMessage(message, memberName) {
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
});
