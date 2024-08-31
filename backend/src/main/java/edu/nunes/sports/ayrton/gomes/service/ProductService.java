package edu.nunes.sports.ayrton.gomes.service;

import edu.nunes.sports.ayrton.gomes.model.Product;
import edu.nunes.sports.ayrton.gomes.persistence.GenericJPARepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;

import java.util.UUID;

@ApplicationScoped
public class ProductService extends GenericJPARepository<Product> {

    public ProductService(){super(Product.class);}



    @Transactional
    public Response deleteProduct(UUID productID) {

        Product productFound = this.findById(productID);

        if (productFound == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok().entity(this.delete(productFound.getId())).build();
    }

}
