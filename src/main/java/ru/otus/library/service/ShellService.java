package ru.otus.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.library.domain.Author;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Genre;

import java.util.Scanner;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShellService {
    private final AuthorService authorService;
    private final GenreService genreService;
    private final BookService bookService;
    private final Scanner scanner = new Scanner(System.in);

    public void createAuthor() {
        System.out.print("Введите имя автора: ");
        String authorName = scanner.nextLine();
        authorService.create(authorName);
    }

    public void createGenre() {
        System.out.print("Введите название жанра: ");
        String genreName = scanner.nextLine();
        genreService.create(genreName);
    }

    public void createBook() {
        System.out.print("Введите название книги: ");
        String bookTitle = scanner.nextLine();

        System.out.print("Введите автора книги: ");
        String bookAuthorName = scanner.nextLine();
        Author author = authorService.findByName(bookAuthorName);
        if (author == null) {
            System.out.print("Автор не найден");
            return;
        }

        System.out.print("Введите жанр: ");
        String genreName = scanner.nextLine();
        Genre genre = genreService.findByName(genreName);
        if (genre == null) {
            System.out.print("Жанр не найден");
            return;
        }

        bookService.create(bookTitle, author.getId(), genre.getId());
    }

    public String getAllBooks() {
        return bookService
                .findAll()
                .stream()
                .map(Book::toString)
                .collect(Collectors.joining("\n"));
    }
}
