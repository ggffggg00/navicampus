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
var image = L.imageOverlay(_floorPlanUrl, bounds).addTo(map);

map.zoomControl.setPosition('bottomright');

map.fitBounds(bounds);
map.on('click', mapClick);


loadGraph((gr) => {
    gr.waypoints.forEach((wp) => {
        let latLng = new L.LatLng(wp.lat, wp.lng)
        if (wp.navigationObjectId !== 0) {
            let marker = new L.Marker(latLng,
                {
                    color: '#FF0000'
                }).addTo(map);
            let idx = wp.id
            state.points[wp.id] = {
                type: "PLACE",
                marker: marker,
                latLng: latLng,
                placeId: wp.navigationObjectId
            }

            marker.on('click', (evt) => markerClick(evt, idx));
        } else {
            let marker = new L.CircleMarker(latLng,
                {
                    radius: 6,
                    color: '#FF0000'
                }).addTo(map);
            let idx = wp.id
            state.points[wp.id] = {
                type: "MIDDLEWARE",
                marker: marker,
                latLng: latLng
            }

            marker.on('click', (evt) => markerClick(evt, idx));
        }
    })

    gr.routes.forEach((el) => {
        let pointStart = state.points[el.nodeIdStart]
        let pointEnd = state.points[el.nodeIdEnd]
        let route = {
            start: {
                lat: pointStart.latLng.lat,
                lng: pointStart.latLng.lng,
                point: el.nodeIdStart
            },
            end: {
                lat: pointEnd.latLng.lat,
                lng: pointEnd.latLng.lng,
                point: el.nodeIdEnd
            },
            polyline: new L.Polyline([pointStart.latLng, pointEnd.latLng], {
                color: 'aqua',
                weight: 3,
                opacity: 1,
                lineCap: "square",
                smoothFactor: 1
            })
        }
        map.addLayer(route.polyline)
        state.routes.push(route)
    })
})


function loadGraph(clb) {
    $.ajax({
        url: "/api/graph/" + _buildingId + "/" + _floor,         /* Куда пойдет запрос */
        method: 'get',             /* Метод передачи (post или get) */
        dataType: 'json',          /* Тип данных в ответе (xml, json, script, html). */
        contentType: "application/json; charset=utf-8",
        success: function (data) {   /* функция которая будет выполнена после успешного запроса.  */
            clb(data)
        }
    })
}

function markerClick(event, pointIdx) {
    if (state.editMode === MODE_APPEND_PLACE) {
        $("#placeSelectorContainer").attr("hidden", false)
        state.selectedPoint = pointIdx;
        if (state.points[pointIdx].placeId !== null) {
            $("#placeSelector option").each((idx, ch) => {
                let optionId = parseInt(ch.getAttribute("value"))
                if (optionId === state.points[pointIdx].placeId) {
                    ch.setAttribute('selected','selected');
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
    // for (const el in state.points) {
    //     if (state.points[el].type === "PLACE" && state.points[el].placeId < 1) {
    //         alert("Не для всех конечных точек проставлена привязка к объекту")
    //         return;
    //     }
    // }

    let waypoints = state.points.map((el, idx) => {
        return {
            id: idx,
            buildingId: _buildingId,
            floor: _floor,
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
        buildingId: _buildingId,
        floor: _floor
    }

    $.ajax({
        url: "http://localhost:8080/api/graph",         /* Куда пойдет запрос */
        method: 'post',             /* Метод передачи (post или get) */
        dataType: 'json',          /* Тип данных в ответе (xml, json, script, html). */
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(data),     /* Параметры передаваемые в запросе. */
        success: function (data) {   /* функция которая будет выполнена после успешного запроса.  */
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

var greenIcon = L.icon({
    iconUrl: '/public/img/placed_marker.png',

    iconSize: [38, 40], // size of the icon
    iconAnchor: [19, 40], // point of the icon which will correspond to marker's location
});

$("#placeSelector").change((evt) => {
    let id = $("#placeSelector option:selected").val()
    state.points[state.selectedPoint].placeId = parseInt(id);
    state.points[state.selectedPoint].marker.setIcon(greenIcon)
})

$("input[type=radio][name=editModeSelector]").change((evt) => {
    state.editMode = evt.currentTarget.getAttribute("value")
})
