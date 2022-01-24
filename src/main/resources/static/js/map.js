/** Constant vales *****/

const START_ZOOM = 6;
const MAX_ZOOM = 21;
const START_LAT = 39.69464371283374;
const START_LNG = -8.13015103340149;

const SINGLE_MARKER_FOCUS_ZOOM = 15;
const PAN_OPTIONS = {"duration": 0.25};

// Creating a Layer object
const googleHybrid = L.tileLayer('http://{s}.google.com/vt/lyrs=s,h&x={x}&y={y}&z={z}', {
    subdomains: ['mt0', 'mt1', 'mt2', 'mt3']
});

const googleTerrain = L.tileLayer('http://{s}.google.com/vt/lyrs=p&x={x}&y={y}&z={z}', {
    subdomains: ['mt0', 'mt1', 'mt2', 'mt3']
});
const tileLayers = new Map();
tileLayers.set("googleHybrid", googleHybrid);
tileLayers.set("googleTerrain", googleTerrain);

let markers = [];

// Creating map options
const mapOptions = {
    center: [START_LAT, START_LNG],
    zoom: START_ZOOM,
    maxZoom: MAX_ZOOM
}

// Creating a map object
const map = new L.map('map', mapOptions);

/***********************/
/** Funcions ***********/

function addLayers(mapToAdd) {
    tileLayers.forEach(function (value, key) {
        console.log(`m[${key}] = ${value}`);
        mapToAdd.addLayer(value);
    });
}

function setLayer(layerName) {
    tileLayers.forEach(function (value, key) {
        if (layerName == key)
            value.setZIndex(1)
        else
            value.setZIndex(0)
    });
}

function addMarkers(locations) {
    for(let i = 0; i < locations.length; i++){
        addMarker(locations[i]);
    }
}

function addMarker(location) {
    let latlgn = [location.lat, location.lon]
    let marker = L.marker(latlgn);
    marker.bindPopup(createMarkerPopUp(location));
    markers.push(marker)
    marker.addTo(map);
}

function removeMarkers() {
    for(let i = 0; i < markers.length; i++){
        map.removeLayer(markers[i]);
    }
    markers = [];
}

/* function setClickToAddMarker() {
    // Test markers!!
    var marker;
    map.on('click', function (e) {
        if (marker)
            map.removeLayer(marker);
        console.log(e.latlng); // e is an event object (MouseEvent in this case)
        marker = L.marker(e.latlng).addTo(map);
        marker.bindPopup("<b>Hello world!</b><br>I am a popup.");
    });
} */

function createMarkerPopUp(location) {
    return `<h6>${location.name}</h6>Desc: ${location.description}<br>x: ${location.x}<br>y: ${location.y}<br>latituda: ${location.lat}<br>longitude: ${location.lon}<br>altitude: ${location.alt}<br>page:${location.page}<br><div style="text-align:center"><buton onclick="setFilterXY(${location.x}, ${location.y})">Use me</button></div>`
}

function focusOnMarkers(locations){
    if (locations.length == 1) {
        focusOnMarker(locations[0]);
    } else {
        let bounds = [];
        for(let i = 0; i < locations.length; i++){
            bounds.push([locations[i].lat, locations[i].lon]);
        }
        map.flyToBounds(bounds, PAN_OPTIONS);
    }
}

function focusOnMarker(location){
    map.flyTo([location.lat, location.lon], SINGLE_MARKER_FOCUS_ZOOM, PAN_OPTIONS);
}

function focusOnLatLng(lat, lng){
    map.flyTo([lat, lng], SINGLE_MARKER_FOCUS_ZOOM, PAN_OPTIONS);
}

/***********************/
/** Start Script *******/

// Adding layers to the map
addLayers(map);
L.control.scale().addTo(map);

