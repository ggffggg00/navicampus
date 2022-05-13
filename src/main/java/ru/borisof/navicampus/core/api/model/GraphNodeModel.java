package ru.borisof.navicampus.core.api.model;

import lombok.Data;
public record GraphNodeModel(
        long index,
        long xPosition,
        long yPosition,
        int objectTypeId,
        String name
        ) {


}
