let wsocketInside;

function onMessageStatistoc(evt) {
    const page = JSON.parse(evt.data);

    console.log(page);
}

function connectInsideWs() {
    wsocketInside = new WebSocket("ws://" +  location.host + "/homework11/inside/ws");
    wsocketInside.onmessage = onMessageStatistoc
}

window.addEventListener("load", connectInsideWs, false);
