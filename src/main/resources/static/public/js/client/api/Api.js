const searchPlace = (placeName) => {
    return [
        {
            id: 20,
            placeName: "Аудитория 428",
            buildingName: "Главный корпус",
            floor: 4
        },
        {
            id: 21,
            placeName: "Аудитория 429",
            buildingName: "Главный корпус",
            floor: 4
        },
        {
            id: 22,
            placeName: "Аудитория 427",
            buildingName: "Главный корпус",
            floor: 4
        },
        {
            id: 23,
            placeName: "Аудитория 426",
            buildingName: "Главный корпус",
            floor: 4
        },
        {
            id: 25,
            placeName: "Толкан",
            buildingName: "Главный корпус",
            floor: 4
        },
    ]
}

const searchShortestPathToPlace = (placeId) => {
    return {
        path: [
            {
                type: "start",
                lat: 142.69677956205794,
                lng: 884.2118986289911,
            }, {
                type: "middle",
                lat: 134.19409882888755,
                lng: 958.680791208277,
            }, {
                type: "end",
                lat: 143.44701609733772,
                lng: 1002.6624190403386,
                placeInfo: {
                    name: "Читальный зал",
                    audience_num: "113",
                    floor: 1
                }
            }
        ]
    }
}

