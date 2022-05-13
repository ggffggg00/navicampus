package ru.borisof.navicampus.core.graph.service;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultUndirectedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.UUID;

@Component
public class InMemoryGraphCore {

    private Graph<UUID, DefaultEdge> graph;

    @PostConstruct
    public void init() {
        graph = new DefaultUndirectedGraph<>(DefaultWeightedEdge.class);
    }


}
