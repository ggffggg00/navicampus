package ru.borisof.navicampus.core.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.borisof.navicampus.core.api.model.GraphDataModel;
import ru.borisof.navicampus.core.dao.service.NaviGraphStoreService;

@RestController
@RequiredArgsConstructor
@RequestMapping("test")
public class TestController {

    private final NaviGraphStoreService naviGraphStoreService;

    @PostMapping
    void test(@RequestBody GraphDataModel graphDataModel) {
        naviGraphStoreService.saveGraphData(graphDataModel);
    }

}
