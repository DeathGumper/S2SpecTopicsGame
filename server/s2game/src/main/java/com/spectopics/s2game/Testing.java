package com.spectopics.s2game;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/test")
public class Testing {
	private Map<String, String> messages = new HashMap<>();
	
	@GetMapping
	public String testEndpoint() {
		return "Test endpoint is working!";
	}

	@GetMapping("/messages")
	public Map<String, String> getMessages() {
		return messages;
	}

	@PostMapping("/message/{msgId}/{name}/{message}")
	public String messageEndpoint(@PathVariable String msgId, @PathVariable String name, @PathVariable String message) {
		messages.put(msgId, name + ": " + message);
		return "Received message: " + message + " from user: " + name;
	}
}
