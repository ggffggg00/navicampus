package ru.borisof.navicampus.ui.client;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.borisof.navicampus.core.service.NavigationObjectService;
import ru.borisof.navicampus.core.service.QrTagService;

import java.util.Base64;

@Controller
@RequiredArgsConstructor
@RequestMapping("admin")
public class AdminController {

    private final NavigationObjectService placeService;
    private final QrTagService qrTagService;

    @GetMapping(("editor/{buildingId}/{floor}"))
    public String floorGraphEditor(@PathVariable final String buildingId, @PathVariable final String floor, Model model) {
        var places = placeService.getAllNavigationObjects();
        model.addAttribute("placesList", places);
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
