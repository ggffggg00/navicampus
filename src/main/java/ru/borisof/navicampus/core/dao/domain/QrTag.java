package ru.borisof.navicampus.core.dao.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Objects;

@Table(name = "qr_tag", schema = "public")
@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class QrTag {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @Column(name = "tag_data", nullable = false, length = 100)
  private String tagData;

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
    QrTag qrTag = (QrTag) o;
    return id != null && Objects.equals(id, qrTag.id);
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}