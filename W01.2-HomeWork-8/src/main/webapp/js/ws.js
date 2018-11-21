let wsocketPublic;

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
    wsocketPublic = new WebSocket("ws://" +  location.host + "/homework8/wspublic");
    wsocketPublic.onmessage = onMessageForexCBR
}

window.addEventListener("load", connectForexCBR, false);
