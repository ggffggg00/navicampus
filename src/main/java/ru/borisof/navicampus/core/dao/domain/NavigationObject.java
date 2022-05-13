package ru.borisof.navicampus.core.dao.domain;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Objects;

@Table(name = "navigation_object", schema = "public")
@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class NavigationObject {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @Column(name = "name", nullable = false, length = 30)
  private String name;

  @ManyToOne(optional = false)
  @JoinColumn(name = "navigation_object_type_id", nullable = false)
  private NavigationObjectType navigationObjectType;

  @OnDelete(action = OnDeleteAction.CASCADE)
  @ManyToOne(optional = false)
  @JoinColumn(name = "graph_node_id", nullable = false)
  private GraphNode graphNode;

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
      return false;
    }
    NavigationObject that = (NavigationObject) o;
    return id != null && Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}