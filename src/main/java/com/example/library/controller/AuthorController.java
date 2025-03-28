package com.example.library.controller;

import com.example.library.dto.AuthorDTO;
import com.example.library.service.AuthorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    public List<AuthorDTO> getAll() {
        return authorService.getAll();
    }

    @GetMapping("/{id}")
    public AuthorDTO getById(@PathVariable Long id) {
        return authorService.getById(id);
    }

    @PostMapping
    public AuthorDTO create(@RequestBody AuthorDTO dto) {
        return authorService.create(dto);
    }

    @PutMapping("/{id}")
    public AuthorDTO update(@PathVariable Long id, @RequestBody AuthorDTO dto) {
        return authorService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        authorService.delete(id);
    }
}
