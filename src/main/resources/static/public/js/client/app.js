getFloorList((data) => {
    floorPlans = data
});


let state = {
    map: {
        floor: 1
    },
    currentPos: {
        lat: _lat,
        lng: _lng,
        building: _buildingId,
        floor: _floor
    },
    routePath: [
        // {
        //     floor: 1,
        //     path: [
        //         {
        //             lat: 0,
        //             lng: 0
        //         }
        //     ]
        // }
    ]

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
changeFloor(state.currentPos.floor)

map.zoomControl.setPosition('bottomright');

map.fitBounds(bounds);
map.on('click', addMarker);

function addMarker(e) {
    console.log(e)
    latLngArr.push(e.latlng)
}


$("#input").on('input', (event) => {
    let queryText = event.target.value.toLowerCase().trim();
    emptySearchResultList()


    if (queryText !== '') {
        searchPlace(queryText, (searchResponse) => {
            searchResponse.forEach((el) => {
                $("#searchResultsList").append("            " +
                    "<li data-id=" + el.id + "  class=\"searchResultItem\">\n" +
                    "                <h4>" + el.name + "</h4>\n" +
                    "                <p>" + el.building.name + "</p>\n" +
                    "                <p>" + el.floor + " этаж</p>\n" +
                    "            </li>")
            })
        });
    }
})

$('body').on('click', 'li.searchResultItem', function (evt) {
    let id = evt.currentTarget.getAttribute("data-id")
    let result = searchShortestPathToPlace(id);
    buildPathOnMap(result.path)
    emptySearchField()
    emptySearchResultList()
});

const emptySearchResultList = () => {
    $("#searchResultsList").empty()
}

const emptySearchField = () => {
    $("#input").val("")
}

function reconstructMapState() {
    setCurrentLocationMarker()
    if (state.routePath !== null && state.routePath.length > 0) {
        let currentFloorPath = state.routePath.filter(el => {
            return el.floor === state.currentPos.floor
        })
        let polylinePoints = currentFloorPath.path.map((el) => {
            return {
                lat: el.lat,
                lng: el.lng
            }
        })
        buildPathOnMap(polylinePoints);
    }
}

function setCurrentLocationMarker() {
    if (state.map.floor === state.currentPos.floor) {
        locationMarker(state.currentPos.lat, state.currentPos.lng)
    }
}

$(".floorControl p").click((evt) => {
    let floor = parseInt(evt.currentTarget.getAttribute("val"))
    changeFloor(floor)
})

function changeFloor(floorId) {
    map.eachLayer(function (layer) {
        map.removeLayer(layer);
    });
    let plan = findFloorById(floorId).planUrl
    image = new L.imageOverlay(plan, bounds).addTo(map);
    state.map.floor = floorId;
    reconstructMapState();
}

function findFloorById(id) {
    return floorPlans.filter(el => el.id === id)[0];
}


function searchPlace (placeName, clb) {
    let req = new XMLHttpRequest();
    req.responseType = 'json';
    req.open('GET', "/api/place/find?q=" + placeName, true);
    req.onload  = function() {
        let resp = this.response
        clb(resp)
    };
    req.send(null);
}
