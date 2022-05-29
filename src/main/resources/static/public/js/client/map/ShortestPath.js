const buildPathOnMap = (path) => {

    let pointList = [];

    path.forEach((el) => {
        let latLng = new L.LatLng(el.lat, el.lng)
        pointList.push(latLng);
        if (el.type === 'end') {
            placeMarker(el.lat, el.lng, el.placeInfo.name, () => {})
        }
    })

    let navipath = new L.Polyline(pointList, {
        kek: true,
        color: 'green',
        weight: 10,
        opacity: 1,
        lineCap: "square",
        smoothFactor: 5
    });
    map.addLayer(navipath)
    map.fitBounds(navipath.getBounds());


}
