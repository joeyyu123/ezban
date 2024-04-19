package ezban.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/host")
public class hostController {

    @GetMapping("/")
    public String host() {
        return "host/hostIndex";
    }

    @GetMapping("/hostTemplate")
    public String hostTemplate() {
        return "host/hostTemplate";
    }
}
