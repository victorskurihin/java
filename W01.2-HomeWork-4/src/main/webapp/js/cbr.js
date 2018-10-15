function getCBRJSON(callback) {
    let xhr = new XMLHttpRequest();
    xhr.open('GET', 'cbrforex', true);
    xhr.responseType = 'json';
    xhr.onload = function () {
        const status = xhr.status;
        if (status === 200) {
            callback(null, xhr.response)
        } else {
            callback(status, xhr.response)
        }
    };
    xhr.send()
}

function fillUl (err, data) {
    if (err !== null) {
        alert('Something went wrong: ' + err);
    } else {
        const arr = data["ValCurs"]["Valute"];
        for (const key in arr) {
            if (arr.hasOwnProperty(key) && /^0$|^[1-9]\d*$/.test(key) && key <= 4294967294) {
                const cur = arr[key]["CharCode"];
                const li = document.getElementById(cur);
                if (li !== null) {
                    li.innerHTML = cur + ": " + arr[key]["Value"];
                }
            }
        }
    }
}
