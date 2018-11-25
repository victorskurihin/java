function fmtDateTimeElem(elem) {
    elem = "0" + elem;
    return elem.substr(elem.length - 2)
}

function dateTimeToStr(t) {
    return t.getFullYear()
        + "-" + fmtDateTimeElem(t.getMonth() + 1)
        + "-" + fmtDateTimeElem(t.getDate())
        + "T" + fmtDateTimeElem(t.getHours())
        + ":" + fmtDateTimeElem(t.getMinutes())
        + ":" + fmtDateTimeElem(t.getSeconds());
}

function setCookie(name, value, expires) {
    document.cookie = name + "=" + value + ((expires === 0)? "": ";expires=" + expires);
}

function getCookie(name) {
    var matches = document.cookie.match(new RegExp(
        "(?:^|; )" + name.replace(/([\.$?*|{}\(\)\[\]\\\/\+^])/g, '\\$1') + "=([^;]*)"
    ));
    return matches ? decodeURIComponent(matches[1]) : undefined;
}

function logToStatistics(pageID) {
    var xhr = new XMLHttpRequest();

    xhr.open('POST', 'stats/insert', true);
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');

    xhr.onreadystatechange = function () {
        if (this.readyState !== 4) return;

        if (this.status === 200) {
            var resp = JSON.parse(this.responseText);
            setCookie("prev_id", resp.visits_stat_id, 0)
        }
    };

    var t = new Date();
    var prev_id = getCookie("prev_id");
    prev_id = prev_id? prev_id: - 1;

    var requestBody = "page_name=" + encodeURIComponent(pageID);
    requestBody += "&prev_id=" + encodeURIComponent(prev_id);
    requestBody += "&client_time=" + encodeURIComponent(dateTimeToStr(t));
    xhr.send(requestBody)
}

function switchCollectionEnabled(){
    var xhr = new XMLHttpRequest();

    xhr.open('POST', 'switch_collection_enabled', true);
    xhr.onreadystatechange = function () {
        if (this.readyState !== 4) return;

        var isCollectionEnabled = document.getElementById("isCollectionEnabled");

        if (this.status === 200) {
            if (isCollectionEnabled.innerText.trim() === "да") {
                isCollectionEnabled.innerText = "нет"
            } else {
                isCollectionEnabled.innerText = "да"
            }
        }
    };
    xhr.send()
}

