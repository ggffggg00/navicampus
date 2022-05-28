package ru.borisof.navicampus;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import ru.borisof.navicampus.core.graph.domain.WaypointEntity;
import ru.borisof.navicampus.core.graph.jdbc.Neo4jNativeQuery;
import ru.borisof.navicampus.core.graph.jdbc.QueryExecutor;
import ru.borisof.navicampus.core.graph.jdbc.query.CreateEdgeQuery;
import ru.borisof.navicampus.core.graph.jdbc.query.FindShortestPathQuery;
import ru.borisof.navicampus.core.graph.jdbc.query.GetAllRoutesAtFloorQuery;
import ru.borisof.navicampus.core.graph.repo.WaypointRepository;
import ru.borisof.navicampus.core.graph.service.GraphService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@SpringBootApplication
public class NavicampusApplication {
    public static void main(String[] args) {
        SpringApplication.run(NavicampusApplication.class, args);
    }

    @Bean
    CommandLineRunner demo(QueryExecutor executor, GraphService graphService) {
        return args -> {
            List<WaypointEntity> arr = Arrays.asList(WaypointEntity.builder()
                            .id(8)
                            .buildingId(1)
                            .floor(2)
                            .lat(1214)
                            .lng(2456)
                            .build(),
                    WaypointEntity.builder()
                            .id(9)
                            .buildingId(1)
                            .floor(2)
                            .lat(6841)
                            .lng(8731)
                            .build(),
                    WaypointEntity.builder()
                            .id(10)
                            .buildingId(1)
                            .floor(2)
                            .lat(787)
                            .lng(315)
                            .build(),
                    WaypointEntity.builder()
                            .id(11)
                            .buildingId(1)
                            .floor(2)
                            .lat(743)
                            .lng(491)
                            .build(),
                    WaypointEntity.builder()
                            .id(12)
                            .buildingId(1)
                            .floor(2)
                            .lat(237)
                            .lng(210)
                            .build(),
                    WaypointEntity.builder()
                            .id(13)
                            .buildingId(1)
                            .floor(2)
                            .lat(795)
                            .lng(746)
                            .build(),
                    WaypointEntity.builder()
                            .id(14)
                            .buildingId(1)
                            .floor(2)
                            .lat(183)
                            .lng(646)
                            .build(),
                    WaypointEntity.builder()
                            .id(15)
                            .buildingId(1)
                            .floor(2)
                            .lat(761)
                            .lng(943)
                            .build(),
                    WaypointEntity.builder()
                            .id(16)
                            .buildingId(1)
                            .floor(2)
                            .lat(221)
                            .lng(450)
                            .build(),
                    WaypointEntity.builder()
                            .id(17)
                            .buildingId(1)
                            .floor(2)
                            .lat(768)
                            .lng(165)
                            .build()
            );

            var res = executor.executeStm(FindShortestPathQuery.builder()
                    .startId(48)
                    .endId(65)
                    .build());


        };
    }

}
