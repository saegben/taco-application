package bened.tacos.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import bened.tacos.models.CustomerReview;
import bened.tacos.repositories.CustomerReviewRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8081, http://localhost:8080")
@RestController
@RequestMapping("/api")
public class CustomerReviewController {
    @Autowired
    CustomerReviewRepository customerReviewRepository;
    @GetMapping("/reviews")
    public ResponseEntity<List<CustomerReview>> getAllReviews(@RequestParam(required = false) String productDescription) {
        try {
            List<CustomerReview> reviews = new ArrayList<CustomerReview>();
            if (productDescription == null)
                customerReviewRepository.findAll().forEach(reviews::add);
            else
                customerReviewRepository.findByProductDescriptionContaining(productDescription).forEach(reviews::add);
            if (reviews.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(reviews, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/reviews/{id}")
    public ResponseEntity<CustomerReview> getReviewById(@PathVariable("id") String id) {
        Optional<CustomerReview> reviewData = customerReviewRepository.findById(id);
        if (reviewData.isPresent()) {
            return new ResponseEntity<>(reviewData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/reviews")
    public ResponseEntity<CustomerReview> createReview(@RequestBody CustomerReview review) {
        try {
            CustomerReview _review = customerReviewRepository.save(new CustomerReview(review.getStars(), review.getProductDescription(), review.getReviewComments(), review.getContactPhone(), review.getContactEmail(), false));
            return new ResponseEntity<>(_review, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/reviews/{id}")
    public ResponseEntity<CustomerReview> updateReview(@PathVariable("id") String id, @RequestBody CustomerReview review) {
        Optional<CustomerReview> reviewData = customerReviewRepository.findById(id);
        if (reviewData.isPresent()) {
            CustomerReview _review = reviewData.get();
            _review.setStars(review.getStars());
            _review.setProductDescription((review.getProductDescription()));
            _review.setReviewComments((review.getReviewComments()));
            _review.setActionNeeded(review.isActionNeeded());
            return new ResponseEntity<>(customerReviewRepository.save(_review), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/reviews/{id}")
    public ResponseEntity<HttpStatus> deleteReview(@PathVariable("id") String id) {
        try {
            customerReviewRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/reviews")
    public ResponseEntity<HttpStatus> deleteAllReviews() {
        try {
            customerReviewRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/reviews/actionNeeded")
    public ResponseEntity<List<CustomerReview>> findByActionNeeded() {
        try {
            List<CustomerReview> reviews = customerReviewRepository.findByActionNeeded(true);
            if (reviews.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(reviews, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}