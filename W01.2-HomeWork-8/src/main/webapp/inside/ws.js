let wsocketInside;

function onMessageStatistoc(evt) {
    const page = JSON.parse(evt.data);

    console.log(page);
}

function connectInsideWs() {
    wsocketInside = new WebSocket("ws://localhost:8080/homework8/inside/ws");
    wsocketInside.onmessage = onMessageStatistoc
}

window.addEventListener("load", connectInsideWs, false);
