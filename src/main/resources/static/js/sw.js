/** Constant vales *****/

/***********************/
/** Funcions ***********/

function ajaxCall(path, query, method,  processResp) {
    const xhttp = new XMLHttpRequest();
    let requestUrl = path + ((query != null) ? "?" + query : "");
    console.log(requestUrl)
    xhttp.onload = function() {
        let jsonResp = JSON.parse(this.responseText);
        console.log(`Resp:\n${this.responseText}\nTo:\n${method} ${requestUrl}`)
        processResp(jsonResp);
    }
    xhttp.open(method, requestUrl);
    xhttp.send();
}

function shutdown() {
    ajaxCall("/sw/actuator/shutdown", null, "POST", exitTabWarning);
}

function exitTabWarning() {
    alert("The Server was turned off, the page will not respond!")
}

function filter(filters, processResp) {
    ajaxCall("/sw/filter", filtersToString(filters), "GET", processResp);
}

function filtersToString(filters) {
    queryFilterStr = "";
    filtersArgs = Object.keys(filters);
    filtersArgs.forEach(arg => {
        queryFilterStr += `${ arg }=${ filters[arg]}&`
    });
    return queryFilterStr;
}
/***********************/
/** Start Script *******/

// Configure panel key press
document.getElementById("shutdownButton").onclick = function (event) {
    shutdown()
}