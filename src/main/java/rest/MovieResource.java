package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entities.Movie;
import utils.EMF_Creator;
import facades.MovieFacade;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

//Todo Remove or change relevant parts before ACTUAL use
@Path("xxx")
public class MovieResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory(
            "pu",
            "jdbc:mysql://localhost:3307/startcode",
            "dev",
            "ax2",
            EMF_Creator.Strategy.CREATE);
    private static final MovieFacade FACADE = MovieFacade.getMovieFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String demo() {
        return "{\"msg\":\"Hello World\"}";
    }

    @Path("count")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getMovieCount() {
        long count = FACADE.getMovieCount();
        //System.out.println("--------------->"+count);
        return "{\"count\":" + count + "}";  //Done manually so no need for a DTO
    }

    @Path("all")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAllMovies() {
        List<Movie> movies = new ArrayList(FACADE.allMovie());

        return Response.ok().entity(GSON.toJson(movies)).build();  //Done manually so no need for a DTO
    }

    @Path("create")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response createMovies() {
        EntityManager em = EMF.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(new Movie("LotR", "Jacob"));
            em.persist(new Movie("SW", "QQ"));

            em.getTransaction().commit();
        } finally {
            em.close();
        }

        return Response.ok().entity(GSON.toJson("gg")).build();  //Done manually so no need for a DTO
    }

    @GET
    @Path("name/{name}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getMovieByDirector(@PathParam("name") String name) {
        Movie m = FACADE.getMovieByDirector(name);

        return Response.ok().entity(GSON.toJson(m)).build();
    }

    @GET
    @Path("id/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getMovieByID(@PathParam("id") Long id) {
        Movie m = FACADE.findByID(id);
        return Response.ok().entity(GSON.toJson(m)).build();
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public void create(Movie entity) {
        throw new UnsupportedOperationException();
    }

    @PUT
    @Path("/{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    public void update(Movie entity, @PathParam("id") int id) {
        throw new UnsupportedOperationException();
    }
}
