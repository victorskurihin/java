function isURL(url) {
    try {
        new URL(url);
        return true
    } catch (e) {
        return false
    }
}

function goSearch() {
    const val = document.getElementById('search').value;
    window.open((isURL(val) ? '' : 'https://google.com/search?q=') + val, '_blank')
}

