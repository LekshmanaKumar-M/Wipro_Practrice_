let stompClient = null;

window.addEventListener('load', () => {
    const layout = document.querySelector('.chat-layout');
    if (!layout) return;

    const role = layout.getAttribute('data-role');             // "CUSTOMER" or "ADMIN"
    const customerId = layout.getAttribute('data-customer-id');
    const chatWindow = document.getElementById('chatWindow');
    const messageInput = document.getElementById('messageInput');
    const sendBtn = document.getElementById('sendBtn');

    // if not on chat page (e.g., admin home)
    if (!customerId || !chatWindow || !messageInput || !sendBtn) {
        return;
    }

    const socket = new SockJS('/ws-chat');
    stompClient = Stomp.over(socket);

    stompClient.connect({}, () => {
        stompClient.subscribe('/topic/chat/' + customerId, (frame) => {
            const body = JSON.parse(frame.body);
            appendMessage(body, role, chatWindow);
        });
    });

    sendBtn.addEventListener('click', () => {
        sendMessage(role, customerId, messageInput);
    });

    messageInput.addEventListener('keypress', (e) => {
        if (e.key === 'Enter' && !e.shiftKey) {
            e.preventDefault();
            sendMessage(role, customerId, messageInput);
        }
    });

    setTimeout(() => {
        chatWindow.scrollTop = chatWindow.scrollHeight;
    }, 200);
});

function sendMessage(role, customerId, input) {
    const text = input.value.trim();
    if (!text || !stompClient) return;

    const payload = {
        customerId: Number(customerId),
        sender: role,    // "CUSTOMER" or "ADMIN"
        message: text
    };

    stompClient.send('/app/chat.send', {}, JSON.stringify(payload));
    input.value = '';
}

function appendMessage(msg, role, chatWindow) {
    const row = document.createElement('div');
    const isUser = msg.sender === role;

    row.classList.add('chat-row');
    row.classList.add(isUser ? 'from-user' : 'from-assistant');

    const avatar = document.createElement('div');
    avatar.classList.add('avatar');
    if (msg.sender === 'CUSTOMER') {
        avatar.textContent = 'C';
    } else if (msg.sender === 'ADMIN') {
        avatar.textContent = 'A';
    } else {
        avatar.textContent = '?';
    }

    const bubble = document.createElement('div');
    bubble.classList.add('bubble');

    const header = document.createElement('div');
    header.classList.add('bubble-header');
    header.textContent = isUser
        ? (role === 'CUSTOMER' ? 'You' : 'You (Admin)')
        : (role === 'CUSTOMER' ? 'MyFin Support' : 'Customer');

    const body = document.createElement('div');
    body.classList.add('bubble-body');
    body.textContent = msg.message;

    const footer = document.createElement('div');
    footer.classList.add('bubble-footer');
    if (msg.timestamp) {
        footer.textContent = msg.timestamp.substring(11, 16);
    }

    bubble.appendChild(header);
    bubble.appendChild(body);
    bubble.appendChild(footer);

    row.appendChild(avatar);
    row.appendChild(bubble);

    chatWindow.appendChild(row);
    chatWindow.scrollTop = chatWindow.scrollHeight;
}
