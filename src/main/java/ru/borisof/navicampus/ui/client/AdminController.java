package ru.borisof.navicampus.ui.client;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.borisof.navicampus.core.common.exception.NotFoundException;
import ru.borisof.navicampus.core.repo.FloorRepo;
import ru.borisof.navicampus.core.service.BuildingService;
import ru.borisof.navicampus.core.service.NavigationObjectService;
import ru.borisof.navicampus.core.service.QrTagService;

import java.util.Base64;

@Controller
@RequiredArgsConstructor
@RequestMapping("admin")
public class AdminController {

    private final NavigationObjectService placeService;
    private final BuildingService buildingService;
    private final QrTagService qrTagService;

    private final FloorRepo floorRepo;

    @GetMapping(("editor/{floor}"))
    public String floorGraphEditor(@PathVariable final int floor, Model model) {
        var floorInfo = floorRepo.findById(floor)
                .orElseThrow(()-> new NotFoundException("Этаж не найден"));
        var places = placeService.getPlacesAtFloor(floor, floorInfo.getBuildingId());

        model.addAttribute("placesList", places);
        model.addAttribute("floorPlanUrl", floorInfo.getPlanUrl());
        model.addAttribute("buildingId", floorInfo.getBuildingId());
        model.addAttribute("floor", floor);
        return "admin/admin";
    }

    @GetMapping("qr/{placeId}")
    public String generateQrCode(@PathVariable final long placeId, Model model) {
        var imgBytes = qrTagService.generateQrCodeForPlace(placeId);
        String base64Img = Base64.getEncoder().encodeToString(imgBytes);
        model.addAttribute("qrcode", base64Img);
        return "admin/qrcode";

    }

}
