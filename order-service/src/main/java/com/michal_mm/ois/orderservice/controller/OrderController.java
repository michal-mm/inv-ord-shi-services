package com.michal_mm.ois.orderservice.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/orders")
public class OrderController {

	@GetMapping
	public List<String> getAllOrders() {
		
		return List.of(
				"Simple order as String #1", 
				"Simple order as String #2",
				"Simple order as String #3");
	}
}
