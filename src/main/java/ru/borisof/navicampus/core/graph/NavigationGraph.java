package ru.borisof.navicampus.core.graph;

import ru.borisof.navicampus.core.dao.domain.GraphNode;

public interface NavigationGraph {

    GraphNode findRoute();

}
