let wsocket;

function onMessageForexCBR(evt) {
    const page = JSON.parse(evt.data);

    if (page.hasOwnProperty('NewsArray')) {
        // console.log("NewsArray: " + page);
        fillUlNews(null, page)
    } else if (page.hasOwnProperty('ValCurs')) {
        // console.log("ValCurs.name: " + page);
        fillUlForex(null, page)
    }
}

function connectForexCBR() {
    wsocket = new WebSocket("ws://localhost:8080/homework8/wspublic");
    wsocket.onmessage = onMessageForexCBR
}

window.addEventListener("load", connectForexCBR, false);
