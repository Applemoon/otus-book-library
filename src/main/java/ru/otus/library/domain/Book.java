package ru.otus.library.domain;

import lombok.Data;

@Data
public class Book {
    private long id;
    private String title;
    private long authorId;
    private long genreId;
}
