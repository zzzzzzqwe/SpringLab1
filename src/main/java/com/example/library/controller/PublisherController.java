package com.example.library.controller;

import com.example.library.model.Publisher;
import com.example.library.service.PublisherService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/publishers")
public class PublisherController {

    private final PublisherService publisherService;

    public PublisherController(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @GetMapping
    public List<Publisher> getAll() {
        return publisherService.getAll();
    }

    @GetMapping("/{id}")
    public Publisher getById(@PathVariable Long id) {
        return publisherService.getById(id);
    }

    @PostMapping
    public Publisher create(@RequestBody Publisher publisher) {
        return publisherService.create(publisher);
    }

    @PutMapping("/{id}")
    public Publisher update(@PathVariable Long id, @RequestBody Publisher publisher) {
        return publisherService.update(id, publisher);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        publisherService.delete(id);
    }
}

