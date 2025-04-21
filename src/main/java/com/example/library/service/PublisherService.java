package com.example.library.service;
import com.example.library.model.Publisher;
import com.example.library.repository.*;
import org.springframework.stereotype.Service;
import java.util.List;



@Service
public class PublisherService {
    private final PublisherRepository publisherRepository;

    public PublisherService(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    public List<Publisher> getAll() {
        return publisherRepository.findAll();
    }

    public Publisher getById(Long id) {
        return publisherRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Publisher not found with id " + id));
    }

    public Publisher create(Publisher publisher) {
        return publisherRepository.save(publisher);
    }

    public Publisher update(Long id, Publisher updated) {
        Publisher existing = getById(id);
        existing.setName(updated.getName());
        return publisherRepository.save(existing);
    }

    public void delete(Long id) {
        publisherRepository.deleteById(id);
    }
}
