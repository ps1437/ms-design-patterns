package com.syscho.ms.registry.ui;

import com.syscho.ms.registry.registry.ServiceInstance;
import com.syscho.ms.registry.registry.ServiceRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collection;
import java.util.List;

@Controller
public class UIController {

    private final ServiceRegistry serviceRegistry;

    public UIController(ServiceRegistry serviceRegistry) {
        this.serviceRegistry = serviceRegistry;
    }

    @GetMapping
    public String showDashboard(Model model) {
        Collection<List<ServiceInstance>> services = serviceRegistry.getAllServices();
        model.addAttribute("services", services);
        return "dashboard";
    }
}
