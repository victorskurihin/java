let wsocketInside;

function onMessageStatistoc(evt) {
    const page = JSON.parse(evt.data);

    console.log(page);
}

function connectInsideWs() {
    wsocketInside = new WebSocket("ws://" +  location.host + "/homework8/inside/ws");
    wsocketInside.onmessage = onMessageStatistoc
}

window.addEventListener("load", connectInsideWs, false);
