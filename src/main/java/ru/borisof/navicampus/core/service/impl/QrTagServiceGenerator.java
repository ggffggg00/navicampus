package ru.borisof.navicampus.core.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.stereotype.Service;
import ru.borisof.navicampus.core.common.exception.NotFoundException;
import ru.borisof.navicampus.core.common.exception.QrGenerateException;
import ru.borisof.navicampus.core.dao.domain.NavigationObject;
import ru.borisof.navicampus.core.dao.domain.QrTag;
import ru.borisof.navicampus.core.repo.QrTagDataRepository;
import ru.borisof.navicampus.core.service.NavigationObjectService;
import ru.borisof.navicampus.core.service.QrTagService;

import static ru.borisof.navicampus.core.common.util.Common.getRandomAlphabeticalString;
import static ru.borisof.navicampus.core.common.util.QrCodeGenerator.getQRCodeImage;

@Service
@RequiredArgsConstructor
public class QrTagServiceGenerator implements QrTagService {

    private final QrTagDataRepository qrRepo;
    private final NavigationObjectService placeService;

    @Value(("${server.hostname}"))
    private String serverHostname;

    private static final int QR_WIDTH = 512;
    private static final int QR_HEIGHT = 512;
    private static final int TAG_LENGTH = 15;


    @Override
    public byte[] generateQrCodeForPlace(final long placeId) {
        var place = placeService.getNavigationObjectById(placeId);
        var generatedTag = getRandomAlphabeticalString(TAG_LENGTH);
        var qrTag = QrTag.builder()
                .navigationObject(place)
                .tagData(generatedTag)
                .build();
        qrRepo.save(qrTag);

        var targetUrl = constructStringForEncoding(generatedTag);
        try {
            return getQRCodeImage(targetUrl ,QR_WIDTH, QR_HEIGHT);
        } catch (Exception e) {
            throw new QrGenerateException("Произошла ошибка при создании QR кода");
        }
    }

    @Override
    public NavigationObject findNavigationObjectByTag(final String tag) {
        return qrRepo.findByTagData(tag)
                .orElseThrow(() -> new NotFoundException("Данная метка не найдена в системе"))
                .getNavigationObject();
    }


    private String constructStringForEncoding(String tag) {
        return "http://" + serverHostname + "/navigate/" + tag;
    }


}
