package com.example.my_app.Modules_Admin.Ship;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/admin/Ship")
public class ShipController {

    private final ShipServices shipServices;

    public ShipController(ShipServices shipServices) {
        this.shipServices = shipServices;
    }
}
