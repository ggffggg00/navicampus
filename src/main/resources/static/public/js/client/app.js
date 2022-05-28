let latLngArr = [];

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
        callback: ()=>{}
    }]
});
var bounds = [[0,0], [359,1852]];
var image = L.imageOverlay('/public/img/sample_floor.svg', bounds).addTo(map);

map.zoomControl.setPosition('bottomright');

map.fitBounds(bounds);
map.on('click', addMarker);

function addMarker(e){
    console.log(e)
    latLngArr.push(e.latlng)
}


$("#input").on('input', (event) => {
    let queryText = event.target.value.toLowerCase().trim();
    emptySearchResultList()


    if (queryText !== '') {
        let searchResponse = searchPlace(queryText);

        searchResponse.forEach((el) => {
            $("#searchResultsList").append("            " +
                "<li data-id=" + el.id + "  class=\"searchResultItem\">\n" +
                "                <h4>" + el.placeName + "</h4>\n" +
                "                <p>" + el.buildingName + "</p>\n" +
                "                <p>" + el.floor + " этаж</p>\n" +
                "            </li>")
        })
    }
})

$('body').on('click', 'li.searchResultItem', function(evt) {
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
