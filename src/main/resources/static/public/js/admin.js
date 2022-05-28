const MODE_APPEND_PLACE = "APPEND_PLACE"
const MODE_APPEND_MIDDLEWARE = "APPEND_MIDDLEWARE"
const MODE_APPEND_ROUTE = "APPEND_ROUTE"

let state = {
    editMode: MODE_APPEND_PLACE,
    selectedPoint: null,
    routeStartPoint: null,
    points: [],
    routes: []
}

var map = L.map('map', {
    crs: L.CRS.Simple,
    minZoom: 2,
    attributionControl: false,
    contextmenu: true,
    rotate: true,
    touchRotate: true,
    rotateControl: {
        closeOnZeroBearing: false
    },
    contextmenuWidth: 140,
    contextmenuItems: [{
        text: 'Show coordinates',
        callback: () => {
        }
    }]
});
var bounds = [[0, 0], [359, 1852]];
var image = L.imageOverlay('/public/img/sample_floor.svg', bounds).addTo(map);

map.zoomControl.setPosition('bottomright');

map.fitBounds(bounds);


map.on('click', mapClick);


function markerClick(event, pointIdx) {
    if (state.editMode === MODE_APPEND_PLACE) {
        $("#placeSelectorContainer").attr("hidden",false)
        state.selectedPoint = pointIdx;
        if (state.points[pointIdx].placeId !== null) {
            console.log($("#placeSelector option"))
            $("#placeSelector option").each((idx, ch) => {
                if (ch.getAttribute("value") === state.points[pointIdx].placeId) {
                    ch.prop('selected', true);
                }
            })

        }
    }
    if (state.editMode === MODE_APPEND_ROUTE) {
        if (state.routeStartPoint === null) {
            state.routeStartPoint = pointIdx
        } else {
            console.log(state.points[state.routeStartPoint])
            let route = {
                start: {
                    lat: state.points[state.routeStartPoint].latLng.lat,
                    lng: state.points[state.routeStartPoint].latLng.lng,
                    point: state.routeStartPoint
                },
                end: {
                    lat: state.points[pointIdx].latLng.lat,
                    lng: state.points[pointIdx].latLng.lng,
                    point: pointIdx
                },
                polyline: new L.Polyline([state.points[state.routeStartPoint].latLng, state.points[pointIdx].latLng], {
                    color: 'aqua',
                    weight: 3,
                    opacity: 1,
                    lineCap: "square",
                    smoothFactor: 1
                })
            }

            map.addLayer(route.polyline)
            state.routes.push(route)
            state.routeStartPoint = null;
        }
    }
}

function sendGraph() {
    let waypoints = state.points.map((el, idx) => {
        return {
            id: idx,
            buildingId: 1,
            floor: 2,
            lat: el.latLng.lat,
            lng: el.latLng.lng,
            navigationObjectId: el.placeId
        }
    })
    let routes = state.routes.map((el) => {
        return {
            nodeIdStart: el.start.point,
            nodeIdEnd: el.end.point
        }
    })

    let data = {
        waypoints: waypoints,
        routes: routes,
        buildingId: 1,
        floor: 2
    }

    $.ajax({
        url: "http://localhost:8080/api/graph",         /* Куда пойдет запрос */
        method: 'post',             /* Метод передачи (post или get) */
        dataType: 'json',          /* Тип данных в ответе (xml, json, script, html). */
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(data),     /* Параметры передаваемые в запросе. */
        success: function(data){   /* функция которая будет выполнена после успешного запроса.  */
            alert(data);            /* В переменной data содержится ответ от index.php. */
        }
    })

}


function mapClick(event) {
    let latLng = new L.LatLng(event.latlng.lat, event.latlng.lng)
    if (state.editMode === MODE_APPEND_PLACE) {
        let marker = new L.Marker(latLng,
            {
                color: '#FF0000'
            }).addTo(map);
        let idx = state.points.push({
            type: "PLACE",
            marker: marker,
            latLng: latLng,
            placeId: null
        }) - 1

        marker.on('click', (evt) => markerClick(evt, idx));
    } else if (state.editMode === MODE_APPEND_MIDDLEWARE) {
        let marker = new L.CircleMarker(latLng,
            {
                radius: 6,
                color: '#FF0000'
            }).addTo(map);
        let idx = state.points.push({
            type: "MIDDLEWARE",
            marker: marker,
            latLng: latLng
        }) - 1

        marker.on('click', (evt) => markerClick(evt, idx));
    }
}

function findMarkerByLatLng(latLng) {
    state.points.forEach((el) => {
        if (el.latLng.lat === latLng.lat && el.latLng.lng === latLng.lng)
            return el;
    })
}

$("#placeSelector").change((evt) => {
    if (state.selectedPoint !== null) {
        let id = $("#placeSelector option:selected").val()
        state.points[state.selectedPoint].placeId = parseInt(id);
    }
})

$("input[type=radio][name=editModeSelector]").change((evt) => {
    state.editMode = evt.currentTarget.getAttribute("value")
})
