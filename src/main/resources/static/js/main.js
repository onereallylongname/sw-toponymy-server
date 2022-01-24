/** Constant vales *****/
const PANEL_DIV_NAME = "panel"
const MAP_LAYER_SELECT_NAME = "mapLayerSelect"
const NAME_CONTAINS_BUTTON_NAME = "mapContainsButton"
const NAME_CONTAINS_INPUT_NAME = "nameContainInput"
const X_INPUT_NAME = "xInput"
const Y_INPUT_NAME = "yInput"
const MAX_DIST_INPUT_NAME = "maxDistInput"
const NAME_LOCATIONS_TABLE_NAME = "locationsTable"

/***********************/
/** Funcions ***********/

// Add layers to select
function addLayersToSelect() {

    let mapLayerSelect = document.getElementById(MAP_LAYER_SELECT_NAME);
    tilerKeys = Array.from(tileLayers.keys());
    tilerKeys.forEach(function (key) {
        console.log(`Adding ${key} to ${MAP_LAYER_SELECT_NAME}`);
        let option = document.createElement("option");
        option.text = key;
        mapLayerSelect.add(option);
    });
    mapLayerSelect.selectedIndex = "0";
    setLayer(tilerKeys[0]);
}

function search() {
    let serachFilters = {
        "nameContains" : document.getElementById(NAME_CONTAINS_INPUT_NAME).value, 
        "x" : document.getElementById(X_INPUT_NAME).value, 
        "y" : document.getElementById(Y_INPUT_NAME).value, 
        "maxDist" : document.getElementById(MAX_DIST_INPUT_NAME).value, 
        };
    removeMarkers();
    filter(serachFilters, addMarkersAndFocus);
}

function makeLocationsTable(locations) {

}

function addMarkersAndFocus(locations) {
    addMarkers(locations)
    deleteRows();
    if (locations.length == 0) return;
    focusOnMarkers(locations);
    addRows(locations);
}

function addRows(locations){
    let table = document.getElementById(NAME_LOCATIONS_TABLE_NAME);
    for(let i = 0; i < locations.length; i++){
        addRow(table, locations[i]);
    }
}

function addRow(table, location) {
    let newRow = table.insertRow(-1);
    newRow.location = location;
    newRow.onclick = function () {
        console.log("Clicked on", location);
        focusOnMarker(location);
    };
    addCell(newRow, location.name);
    addCell(newRow, location.description);
    addCell(newRow, location.x);
    addCell(newRow, location.y);
    addCell(newRow, location.lat);
    addCell(newRow, location.lon);
    addCell(newRow, location.alt);
    addCell(newRow, location.page);
}

function addCell(row, text) {
    let nameCell = row.insertCell(-1);
    nameCell.appendChild(document.createTextNode(text));
}

function deleteRows() {
    let table = document.getElementById(NAME_LOCATIONS_TABLE_NAME);
    let numRows = table.rows.length
    for(let i = numRows -1; i > 0; i--){
        table.deleteRow(i);
    }
}

function setFilterXY(x, y) {
    document.getElementById(X_INPUT_NAME).value = x;
    document.getElementById(Y_INPUT_NAME).value = y;
}

/***********************/
/** Start Script *******/

// Add layers to select
addLayersToSelect();
// Configure map layer switch on select new option
document.getElementById(MAP_LAYER_SELECT_NAME).onchange = function (evt) {
    var value = evt.target.value;
    setLayer(value);
};

// Configure search button
document.getElementById(NAME_CONTAINS_BUTTON_NAME).onclick = function () {
    search()
}

// Configure panel key press
document.getElementById(PANEL_DIV_NAME).onkeyup = function (event) {
    // console.log(event);
    if(event.key == "Enter")
        search()
}

// Set focus on input box
document.getElementById(NAME_CONTAINS_INPUT_NAME).focus();
document.getElementById(NAME_CONTAINS_INPUT_NAME).select();
