floorPlans = getFloorList(_buildingId)

let state = {
    map: {
        floor: 1,
        building: 1
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
    contextmenuWidth: 140,
    contextmenuItems: [{
        text: 'Show coordinates',
        callback: () => {
        }
    }]
});
var bounds = [[0, 0], [359, 1852]];
changeFloorOrBuilding(state.currentPos.floor)

map.zoomControl.setPosition('bottomright');


$("#input").on('input', (event) => {
    let queryText = event.target.value.toLowerCase().trim();
    emptySearchResultList()


    if (queryText !== '') {
        searchPlace(queryText, (searchResponse) => {
            searchResponse.forEach((el) => {
                $("#searchResultsList").append("            " +
                    "<li data-id=" + el.id + "  class=\"searchResultItem\">\n" +
                    "                <p>" + el.name + "</p>\n" +
                    "                <p class='text'>" + el.building.name + "</p>\n" +
                    "                <p class='text'>" + el.floor.name + " этаж</p>\n" +
                    "            </li>")
            })
        });
    }
})

$('body').on('click', 'li.searchResultItem', function (evt) {
    let id = evt.currentTarget.getAttribute("data-id")
    let result = searchShortestPathToPlace(_placeId, id);

    let routePath = {}
    result.forEach((el) => {
        if (routePath[el.floor] === undefined)
            routePath[el.floor] = []
        routePath[el.floor].push(el)
    })
    state.routePath = routePath

    clearMapPath()
    buildPathOnMap(routePath[state.map.floor])
    emptySearchField()
    emptySearchResultList()
});

const refreshFloorList = () => {
    let floorControl = $(".floorControl");
    floorControl.empty()
    floorPlans.forEach(el => {
        if (el.buildingId === state.map.building)
            floorControl.append('<p val="' + el.id + '">' + el.name + '</p>')
    })
}

const emptySearchResultList = () => {
    $("#searchResultsList").empty()
}

const emptySearchField = () => {
    $("#input").val("")
}

function reconstructMapState() {
    setCurrentLocationMarker()
    if (state.routePath[state.map.floor] !== undefined) {
        buildPathOnMap(state.routePath[state.map.floor])
    } else {
        map.fitBounds(bounds);
    }
}

function setCurrentLocationMarker() {
    if (state.map.floor === state.currentPos.floor) {
        locationMarker(state.currentPos.lat, state.currentPos.lng)
    }
}

$('.floorControl').on('click', 'p', function (evt) {
    let floor = parseInt(evt.currentTarget.getAttribute("val"))
    changeFloorOrBuilding(floor)
})

$(".buildingControl").change((evt) => {
    let id = parseInt($(".buildingControl option:selected").attr("val"))
    state.map.building = id
    refreshFloorList()
})

function changeFloorOrBuilding(floorId) {
    map.eachLayer(function (layer) {
        map.removeLayer(layer);
    });
    let plan = findFloorByBuildingAndFloor(floorId).planUrl
    image = new L.imageOverlay(plan, bounds).addTo(map);
    state.map.floor = floorId;
    reconstructMapState();
}

function clearMapPath() {
    map.eachLayer(function (layer) {
        if (layer.options.kek !== undefined)
            map.removeLayer(layer);
    });
}

function findFloorByBuildingAndFloor(floor) {
    return floorPlans.filter(el => el.id === floor)[0];
}


function searchPlace(placeName, clb) {
    let req = new XMLHttpRequest();
    req.responseType = 'json';
    req.open('GET', "/api/place/find?q=" + placeName, true);
    req.onload = function () {
        let resp = this.response
        clb(resp)
    };
    req.send(null);
}
