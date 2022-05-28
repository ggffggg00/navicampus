const buildPathOnMap = (path) => {

    let pointList = [];

    path.forEach((el) => {
        let latLng = new L.LatLng(el.lat, el.lng)
        pointList.push(latLng);
        if (el.type === 'end') {
            placeMarker(el.lat, el.lng, el.placeInfo.name, () => {
                console.log("СОСИ ХУЙ")
            })
        }
    })

    let navipath = new L.Polyline(pointList, {
        color: 'green',
        weight: 10,
        opacity: 1,
        lineCap: "square",
        smoothFactor: 1
    });
    map.addLayer(navipath)
    map.fitBounds(navipath.getBounds());


}
