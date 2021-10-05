package ru.otus.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.otus.library.domain.Author;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    Author findByName(String name);
}
