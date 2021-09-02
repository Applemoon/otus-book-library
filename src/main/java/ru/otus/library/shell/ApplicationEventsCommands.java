package ru.otus.library.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.library.domain.Author;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Genre;
import ru.otus.library.service.AuthorService;
import ru.otus.library.service.BookService;
import ru.otus.library.service.GenreService;

import java.util.Scanner;
import java.util.stream.Collectors;

@ShellComponent
@RequiredArgsConstructor
public class ApplicationEventsCommands {

    private final AuthorService authorService;
    private final GenreService genreService;
    private final BookService bookService;
    private final Scanner scanner = new Scanner(System.in);

    @ShellMethod(value = "Create author", key = {"ca", "cauthor"})
    public String createAuthor() {
        System.out.print("Введите имя автора: ");
        String authorName = scanner.nextLine();
        authorService.create(authorName);
        return "Автор " + authorName + " создан";
    }

    @ShellMethod(value = "Get all authors", key = {"gaa", "authors"})
    public String getAllAuthors() {
        return authorService
                .findAll()
                .stream()
                .map(Author::toString)
                .collect(Collectors.joining("\n"));
    }

    @ShellMethod(value = "Create genre", key = {"cg", "cgenre"})
    public String createGenre() {
        System.out.print("Введите название жанра: ");
        String genreName = scanner.nextLine();
        genreService.create(genreName);
        return "Жанр " + genreName + " создан";
    }

    @ShellMethod(value = "Get all genres", key = {"gag", "genres"})
    public String getAllGenres() {
        return genreService
                .findAll()
                .stream()
                .map(Genre::toString)
                .collect(Collectors.joining("\n"));
    }

    @ShellMethod(value = "Create book", key = {"cb", "cbook"})
    public String createBook() {
        System.out.print("Введите название книги: ");
        String title = scanner.nextLine();

        System.out.print("Введите автора книги: ");
        String bookAuthorName = scanner.nextLine();
        Author author = authorService.findByName(bookAuthorName);
        if (author == null) {
            return "Автор не найден";
        }

        System.out.print("Введите жанр: ");
        String genreName = scanner.nextLine();
        Genre genre = genreService.findByName(genreName);
        if (genre == null) {
            return "Жанр не найден";
        }

        bookService.create(title, author.getId(), genre.getId());
        return "Книга создана";
    }

    @ShellMethod(value = "Get all books", key = {"gab", "books"})
    public String getAllBooks() {
        return bookService
                .findAll()
                .stream()
                .map(Book::toString)
                .collect(Collectors.joining("\n"));
    }

    @ShellMethod(value = "Get book by title", key = {"gbt", "book"})
    public String getBookByTitle() {
        System.out.print("Введите название книги: ");
        String title = scanner.nextLine();
        Book book = bookService.findByTitle(title);
        if (book == null) {
            return "Книга под названием " + title + " не найдена";
        }
        return book.toString();
    }

    @ShellMethod(value = "Update book", key = {"ub", "ubook"})
    public String updateBook() {
        System.out.print("Введите id книги: ");
        long id = Long.parseLong(scanner.nextLine());
        Book book = bookService.findById(id);
        if (book == null) {
            return "Книга с id " + id + " не найдена";
        }

        System.out.print("Введите новое название книги: ");
        String newTitle = scanner.nextLine();

        System.out.print("Введите нового автора книги: ");
        String newAuthorName = scanner.nextLine();
        Author newAuthor = authorService.findByName(newAuthorName);
        if (newAuthor == null) {
            return "Автор не найден";
        }

        System.out.print("Введите новый жанр: ");
        String newGenreName = scanner.nextLine();
        Genre newGenre = genreService.findByName(newGenreName);
        if (newGenre == null) {
            return "Жанр не найден";
        }

        bookService.update(id, newTitle, newAuthor.getId(), newGenre.getId());
        return "Книга обновлена";
    }

    @ShellMethod(value = "Delete book", key = {"db", "dbook"})
    public String deleteBook() {
        System.out.print("Введите id книги: ");
        long id = Long.parseLong(scanner.nextLine());
        Book book = bookService.findById(id);
        if (book == null) {
            return "Книга с id " + id + " не найдена";
        }
        if (bookService.deleteById(id)) {
            return "Книга удалена";
        }
        return "Не удалось удалить книгу";
    }
}
