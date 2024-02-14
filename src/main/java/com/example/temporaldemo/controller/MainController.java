package com.example.temporaldemo.controller;

import com.example.temporaldemo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {
    @Autowired
    OrderService orderService;
    @PostMapping("/startWorkflow")
    public String createOrder(@RequestParam("id") String id){
        orderService.placeOrder(id);
        return "Order placed";
    }
    @PostMapping("/orderAccepted")
    public String orderAccepted(@RequestParam("id") String id){
        orderService.makeOrderAccepted(id);
        return "Order accepted";
    }
    @PostMapping("/orderPickedUp")
    public String orderPickedUp(@RequestParam("id") String id){
        orderService.makeOrderPickedUp(id);
        return "Order picked up";
    }
    @PostMapping("/orderDelivered")
    public String orderDelivered(@RequestParam("id") String id){
        orderService.makeOrderDelivered(id);
        return "order Delivered";
    }
}
