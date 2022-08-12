package bened.tacos.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import bened.tacos.models.CustomerReview;

import java.util.List;

public interface CustomerReviewRepository extends MongoRepository<CustomerReview, String> {
    List<CustomerReview> findByProductDescriptionContaining(String productDescription);
    List<CustomerReview> findByActionNeeded(boolean actionNeeded);
}