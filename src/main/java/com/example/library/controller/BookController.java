package com.example.library.controller;

import com.example.library.dto.BookDTO;
import com.example.library.service.BookService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public List<BookDTO> getAll() {
        return bookService.getAll();
    }

    @GetMapping("/{id}")
    public BookDTO getById(@PathVariable Long id) {
        return bookService.getById(id);
    }

    @PostMapping
    public BookDTO create(@RequestBody BookDTO dto) {
        return bookService.create(dto);
    }

    @PutMapping("/{id}")
    public BookDTO update(@PathVariable Long id, @RequestBody BookDTO dto) {
        return bookService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        bookService.delete(id);
    }
}
