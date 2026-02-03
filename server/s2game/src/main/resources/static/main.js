// main.js — simple caller for Spring Boot endpoint at /test

var serverUrl = 'http://localhost:7831';

async function callTest() {
  const outEl = document.getElementById('serverResponse');
  try {
    const resp = await fetch(serverUrl + '/test', { method: 'GET', cache: 'no-store' });
    if (!resp.ok) throw new Error(`HTTP ${resp.status}`);
    const text = await resp.text();
    console.log('Server response:', text);
    if (outEl) outEl.textContent = text;
    return text;
  } catch (err) {
    console.error('Request failed:', err);
    if (outEl) outEl.textContent = 'Error: ' + err.message;
    throw err;
  }
}

async function sendMessage(name, msg) {
    try {
        const msgId = crypto.randomUUID();
        const resp = await fetch(serverUrl + '/test/message/' + msgId + '/' + name + '/' + msg, {
            method: 'POST'
        });
        if (!resp.ok) throw new Error(`HTTP ${resp.status}`);
        const text = await resp.text();
        console.log('Message response:', text);
    } catch (err) {
        console.error('Message request failed:', err);
        throw err;
    }
}

async function getMessages() {
    try {
        const resp = await fetch(serverUrl + '/test/messages', {
            method: 'GET',
            cache: 'no-store'
        });
        if (!resp.ok) throw new Error(`HTTP ${resp.status}`);

        // get the hash map of messages
        const json = await resp.json();
        console.log('Messages retrieved:', json);
        return json;
    } catch (err) {
        console.error('Get messages request failed:', err);
        throw err;
    }
}

// expose for console usage
window.callTest = callTest;
window.sendMessage = sendMessage;
window.getMessages = getMessages;

// auto-run if an element with id `autoCall` is present and true
document.addEventListener('DOMContentLoaded', () => {
  const auto = document.getElementById('autoCall');
  if (auto && (auto.getAttribute('data-auto') === 'true')) {
    callTest().catch(() => {});
  }
});
