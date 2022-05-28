package ru.borisof.navicampus.core.graph.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.List;

@Node("Waypoint")
@Data
@AllArgsConstructor
@Builder
public class WaypointEntity {

    @Id
    @GeneratedValue
    private long id;

    private int buildingId;

    private double lat;

    private double lng;

    private int floor;

    private long navigationObjectId;


}
