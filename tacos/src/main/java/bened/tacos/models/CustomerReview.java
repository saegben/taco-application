package bened.tacos.models;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@RequiredArgsConstructor
@Document(collection="customerReviews")
public class CustomerReview {
    @Id
    private String id;
    private Integer stars;
    private String productDescription;
    private String reviewComments;
    private String contactPhone;
    private String contactEmail;
    private boolean actionNeeded;

    public CustomerReview(Integer stars, String productDescription, String reviewComments,
                          String contactPhone, String contactEmail, boolean actionNeeded) {
        this.stars = stars;
        this.productDescription = productDescription;
        this.reviewComments = reviewComments;
        this.contactPhone = contactPhone;
        this.contactEmail = contactEmail;
        this.actionNeeded = actionNeeded;
    }
}
