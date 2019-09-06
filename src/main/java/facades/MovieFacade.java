package facades;

import entities.Movie;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * Rename Class to a relevant name Add add relevant facade methods
 */
public class MovieFacade {

    private static MovieFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private MovieFacade() {
    }

    /**
     *
     * @param _emf
     * @return an instance of this facade class.
     */
    public static MovieFacade getMovieFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new MovieFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    //TODO Remove/Change this before use
    public long getMovieCount() {
        EntityManager em = emf.createEntityManager();
        try {
            long movieCount = (long) em.createQuery("SELECT COUNT(n) FROM Movie n").getSingleResult();
            return movieCount;
        } finally {
            em.close();
        }

    }

    public Movie getMovieByDirector(String director) {
        EntityManager em = emf.createEntityManager();
        try {
            Query m = em.createQuery("Select m from Movie m Where m.director = :director", Movie.class);
            m.setParameter("director", director);
            return (Movie) m.getSingleResult();
        } finally {
            em.close();
        }

    }

    public List<Movie> allMovie() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Movie> query
                    = em.createQuery("SELECT m FROM Movie m", Movie.class);
            // List<Customer> customers = query.getResultList();
            // return customers; // disse 2 linjer er det samme som der allerede står bare delt op.
            return query.getResultList();
        } finally {
            em.close();

        }
    }

    public Movie createMovie(String movieName, String director) {
        Movie m = new Movie(movieName, director);
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(m);
            em.getTransaction().commit();
            return m;
        } finally {
            em.close();
        }
    }

    public Movie findByID(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            Movie m = em.find(Movie.class, id);
            return m;
        } finally {
            em.close();
        }
    }

}
