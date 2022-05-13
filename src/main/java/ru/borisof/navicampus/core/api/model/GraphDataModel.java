package ru.borisof.navicampus.core.api.model;

import java.util.List;

public record GraphDataModel(
        int buildingId,
        int floor,
        List<GraphNodeModel> nodeList,
        List<GraphEdgeModel> edgeModelList
) {
}
