package ru.otus.library.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.library.domain.Author;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class AuthorRepository {

    @PersistenceContext
    private EntityManager em;

    public void create(Author author) {
        if (author.getId() == 0) {
            em.persist(author);
//            return author;
        }
        em.merge(author);
//        return em.merge(author);
    }

    public Author findByName(String name) {
        TypedQuery<Author> query = em.createQuery("select a from Author a where a.name = :name", Author.class);
        query.setParameter("name", name);
        return query.getSingleResult();
    }

    public List<Author> findAll() {
        return em.createQuery("select a from Author a", Author.class).getResultList();
    }

}
