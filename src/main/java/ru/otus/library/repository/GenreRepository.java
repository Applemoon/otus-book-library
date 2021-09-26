package ru.otus.library.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.library.domain.Genre;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class GenreRepository {

    @PersistenceContext
    private EntityManager em;

    public void create(Genre genre) {
        if (genre.getId() == 0) {
            em.persist(genre);
//            return genre;
        }
        em.merge(genre);
//        return em.merge(genre);
    }

    public Genre findByName(String name) {
        TypedQuery<Genre> query = em.createQuery("select g from Genre g where g.name = :name", Genre.class);
        query.setParameter("name", name);
        return query.getSingleResult();
    }

    public List<Genre> findAll() {
        return em.createQuery("select g from Genre g", Genre.class).getResultList();
    }

}