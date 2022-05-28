package ru.borisof.navicampus.core.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.borisof.navicampus.core.dao.domain.NavigationObject;
import ru.borisof.navicampus.core.dao.domain.QrTag;

import java.util.Optional;

public interface QrTagDataRepository extends JpaRepository<QrTag, Long> {

    Optional<QrTag> findByNavigationObjectId(final Long navigationObject_id);

    Optional<QrTag> findByTagData(final String tagData);

}
