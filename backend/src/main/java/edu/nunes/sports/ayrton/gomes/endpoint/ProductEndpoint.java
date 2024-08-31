package edu.nunes.sports.ayrton.gomes.endpoint;

import edu.nunes.sports.ayrton.gomes.model.Product;
import edu.nunes.sports.ayrton.gomes.service.ProductService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.UUID;

@Path("/product")
public class ProductEndpoint {

    @Inject
    ProductService productService;

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllProduct(){
        return Response.ok().entity(productService.list()).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addProduct(Product product) {
        return Response.ok().entity(productService.insert(product)).build();
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateProduct(Product product) {
        return Response.ok().entity(productService.update(product)).build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteProduct(@PathParam("id") UUID productId) {
        return Response.ok().entity(productService.deleteProduct(productId)).build();
    }

}

