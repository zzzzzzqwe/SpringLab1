package com.example.library.service;

import com.example.library.dto.BookDTO;
import com.example.library.model.Author;
import com.example.library.model.Book;
import com.example.library.model.Category;
import com.example.library.model.Publisher;
import com.example.library.repository.*;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final PublisherRepository publisherRepository;
    private final CategoryRepository categoryRepository;

    public BookService(BookRepository bookRepository,
                       AuthorRepository authorRepository,
                       PublisherRepository publisherRepository,
                       CategoryRepository categoryRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.publisherRepository = publisherRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<BookDTO> getAll() {
        return bookRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public BookDTO getById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));
        return toDTO(book);
    }

    public BookDTO create(BookDTO dto) {
        Book book = new Book();
        book.setTitle(dto.getTitle());
        book.setAuthor(getAuthor(dto.getAuthorId()));
        book.setPublisher(getPublisher(dto.getPublisherId()));
        book.setCategories(getCategories(dto.getCategoryIds()));

        Book saved = bookRepository.save(book);
        return toDTO(saved);
    }

    public BookDTO update(Long id, BookDTO dto) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        book.setTitle(dto.getTitle());
        book.setAuthor(getAuthor(dto.getAuthorId()));
        book.setPublisher(getPublisher(dto.getPublisherId()));
        book.setCategories(getCategories(dto.getCategoryIds()));

        Book updated = bookRepository.save(book);
        return toDTO(updated);
    }

    public void delete(Long id) {
        bookRepository.deleteById(id);
    }

    private BookDTO toDTO(Book book) {
        List<Long> categoryIds = book.getCategories().stream().map(Category::getId).toList();
        return new BookDTO(
                book.getId(),
                book.getTitle(),
                book.getAuthor().getId(),
                book.getPublisher().getId(),
                categoryIds
        );
    }

    private Author getAuthor(Long id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Author not found"));
    }

    private Publisher getPublisher(Long id) {
        return publisherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Publisher not found"));
    }

    private List<Category> getCategories(List<Long> ids) {
        return categoryRepository.findAllById(ids);
    }
}
