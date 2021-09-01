package ru.otus.library.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.library.service.ShellService;

@ShellComponent
@RequiredArgsConstructor
public class ApplicationEventsCommands {

    private final ShellService shellService;

    @ShellMethod(value = "Create author", key = {"a", "author"})
    public String createAuthor() {
        shellService.createAuthor();
        return "";
    }

    @ShellMethod(value = "Get all authors", key = {"as", "authors"})
    public String getAuthors() {
        return ""; // shellService.getAllAuthors();
    }

    @ShellMethod(value = "Create genre", key = {"g", "genre"})
    public String genre() {
        shellService.createGenre();
        return "";
    }

    @ShellMethod(value = "Get all genres", key = {"gs", "genres"})
    public String getGenres() {
        return ""; // shellService.getAllGenres();
    }

    @ShellMethod(value = "Create book", key = {"b", "book"})
    public String book() {
        shellService.createBook();
        return "";
    }

    @ShellMethod(value = "Get all books", key = {"bs", "books"})
    public String books() {
        return shellService.getAllBooks();
    }
}
