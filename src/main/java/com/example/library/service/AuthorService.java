package com.example.library.service;

import com.example.library.dto.AuthorDTO;
import com.example.library.model.Author;
import com.example.library.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public List<AuthorDTO> getAll() {
        return authorRepository.findAll()
                .stream()
                .map(author -> new AuthorDTO(author.getId(), author.getName()))
                .collect(Collectors.toList());
    }

    public AuthorDTO getById(Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Author not found"));
        return new AuthorDTO(author.getId(), author.getName());
    }

    public AuthorDTO create(AuthorDTO dto) {
        Author author = new Author();
        author.setName(dto.getName());
        Author saved = authorRepository.save(author);
        return new AuthorDTO(saved.getId(), saved.getName());
    }

    public AuthorDTO update(Long id, AuthorDTO dto) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Author not found"));
        author.setName(dto.getName());
        Author updated = authorRepository.save(author);
        return new AuthorDTO(updated.getId(), updated.getName());
    }

    public void delete(Long id) {
        authorRepository.deleteById(id);
    }
}
