package ru.borisof.navicampus.core.service;

import ru.borisof.navicampus.core.dao.domain.NavigationObject;

public interface QrTagService {

    byte[] generateQrCodeForPlace(long placeId);

    NavigationObject findNavigationObjectByTag(String tag);


}
