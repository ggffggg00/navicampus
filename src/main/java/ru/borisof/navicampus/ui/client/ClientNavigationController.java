package ru.borisof.navicampus.ui.client;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("navigate")
public class ClientNavigationController {


    @GetMapping("{qtTag}")
    public String clientNavigationPage(@PathVariable final String qtTag) {
        return "client/clientNav";
    }

}
