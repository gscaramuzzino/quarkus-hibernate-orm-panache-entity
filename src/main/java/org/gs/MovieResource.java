package org.gs;


import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

@Path("/movies")
public class MovieResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        List<Movie> movies = Movie.listAll();
        return Response.ok(movies).build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("id") Long id) {
         return Movie.findByIdOptional(id)
                 .map(movie -> Response.ok(movie).build())
                 .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @GET
    @Path("country/{country}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getByCountry(@PathParam("country") String country) {
        List<Movie> movies = Movie.list("SELECT m FROM Movie m WHERE m.country = ?1 ORDER BY id DESC", country);
        return Response.ok(movies).build();
    }


    @GET
    @Path("/title/{title}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getByTitle(@PathParam("title") String title) {
        return Movie.find("title", title)
                .singleResultOptional()
                .map(movie -> Response.ok(movie).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @POST
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(Movie movie) {
        Movie.persist(movie);
        if(movie.isPersistent()) {
            return Response.created(URI.create("/movies/"+ movie.id)).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response deleteBydId(@PathParam("id") Long id) {
        boolean deleted = Movie.deleteById(id);
        if(deleted) {
            return Response.noContent().build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

}