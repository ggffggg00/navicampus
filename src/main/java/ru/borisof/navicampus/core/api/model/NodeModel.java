package ru.borisof.navicampus.core.api.model;

import lombok.Data;

@Data
public final class NodeModel {
        private long index;
        private double lat;
        private double lng;
        private long navigationObjectId;

}
