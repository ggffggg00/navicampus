package ru.borisof.navicampus.core.graph.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PathEntry {

    public static final String TYPE_START = "start";
    public static final String TYPE_MIDDLE = "middle";
    public static final String TYPE_END = "end";

    private String type;
    private double lat;
    private double lng;
    private int floor;
    private int buildingId;
    private PlaceInfo placeInfo;

    @Data
    @AllArgsConstructor
    public static class PlaceInfo {
        private String name;
    }



}
