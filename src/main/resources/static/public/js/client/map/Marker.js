

const routeMarker = (lat, lng, map) => {
    return new L.CircleMarker(new L.LatLng(lat, lng),
        {
            radius: 4,
            color: '#FF0000'
        }).addTo(map);
}

const placeMarker = (lat, lng, name, clickCallback) => {
    let marker = new L.Marker( new L.LatLng(lat, lng),
        {
            color: '#FF0000'
        }).addTo(map).on('clock', clickCallback);
    marker.bindPopup(name)
}

const locationMarker = (lat, lng) => {
    let marker = new L.Marker( new L.LatLng(lat, lng),
        {
            color: '#109708'
        }).addTo(map);
    marker.bindPopup("Вы здесь")
}

