package ru.borisof.navicampus.core.dao.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Objects;
import java.util.UUID;

@Table(name = "graph_edge", schema = "public")
@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GraphEdge {

  @Id
  @Column(name = "id", nullable = false)
  private UUID id;

  // аннотация ниже позволяет нам при удалении ноды графа сразу дропать нахуй связи из базы с помощью каскада
  @OnDelete(action = OnDeleteAction.CASCADE)
  @ManyToOne(optional = false, cascade = CascadeType.ALL)
  @JoinColumn(name = "first_node_id", nullable = false)
  private GraphNode firstNode;

  // аннотация ниже позволяет нам при удалении ноды графа сразу дропать нахуй связи из базы с помощью каскада
  @OnDelete(action = OnDeleteAction.CASCADE)
  @ManyToOne(optional = false, cascade = CascadeType.ALL)
  @JoinColumn(name = "second_node_id", nullable = false)
  private GraphNode secondNode;

  @Column(name = "weight", nullable = false)
  private Double weight;

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
      return false;
    }
    GraphEdge graphEdge = (GraphEdge) o;
    return id != null && Objects.equals(id, graphEdge.id);
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}