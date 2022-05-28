package ru.borisof.navicampus.core.dao.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;

@Table(name = "building", schema = "public")
@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Building {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false, length = 30)
    private String name;

    @Column(name = "floor_count", nullable = false)
    private int floorCount;



    @ElementCollection
    @CollectionTable(name = "building_property",
                     joinColumns = {@JoinColumn(name = "building_id", referencedColumnName = "id")})
    @MapKeyColumn(name = "key")
    @Column(name = "value")
    private Map<String, String> properties;

    @OneToMany(mappedBy = "building", orphanRemoval = true)
    private Collection<FloorEntity> floorList = new ArrayList<>();


    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        Building building = (Building) o;
        return id != null && Objects.equals(id, building.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}