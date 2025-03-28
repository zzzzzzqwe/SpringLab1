package com.example.library.dto;

import java.util.List;

public class LibraryDTO {
    private Long id;
    private String name;
    private List<Long> bookIds;

    public LibraryDTO() {}

    public LibraryDTO(Long id, String name, List<Long> bookIds) {
        this.id = id;
        this.name = name;
        this.bookIds = bookIds;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Long> getBookIds() {
        return bookIds;
    }

    public void setBookIds(List<Long> bookIds) {
        this.bookIds = bookIds;
    }
}
