package ru.borisof.navicampus.ui.client;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.borisof.navicampus.core.common.exception.NotFoundException;
import ru.borisof.navicampus.core.dao.domain.FloorEntity;
import ru.borisof.navicampus.core.graph.repo.WaypointRepository;
import ru.borisof.navicampus.core.graph.service.GraphService;
import ru.borisof.navicampus.core.service.QrTagService;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("navigate")
@RequiredArgsConstructor
public class ClientNavigationController {

    private final QrTagService qrTagService;

    private final GraphService graphService;

    @GetMapping("{qrTag}")
    public String clientNavigationPage(@PathVariable final String qrTag, Model model) {


        var place = qrTagService.findNavigationObjectByTag(qrTag);
        var waypoint = graphService.findByPlaceId(place.getId());
        var floorPlanUrlList = place.getBuilding().getFloorList().stream()
                .map(FloorEntity::getPlanUrl)
                .collect(Collectors.joining(";"));
        var floorList = place.getBuilding().getFloorList();

        model.addAttribute("floor", waypoint.getFloor());
        model.addAttribute("buildingId", waypoint.getBuildingId());
        model.addAttribute("lat", waypoint.getLat());
        model.addAttribute("lng", waypoint.getLng());
        model.addAttribute("floorPlanUrls", floorPlanUrlList);
        model.addAttribute("floorList", floorList);
        return "client/clientNav";
    }

}
