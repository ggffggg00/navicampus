package ru.borisof.navicampus.core.dao.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.borisof.navicampus.core.dao.domain.GraphEdge;

public interface GraphEdgeRepo extends JpaRepository<GraphEdge, Long> {

}
