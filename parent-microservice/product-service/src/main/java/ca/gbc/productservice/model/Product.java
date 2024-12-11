// This line declares the package where the class is located.
package ca.gbc.productservice.model;

//@AllArgsConstructor:
// Generates a constructor with one parameter for each field in your class.
import lombok.AllArgsConstructor;

//@Builder: Enables the builder pattern for creating instances of the class.
import lombok.Builder;

//@Data: Generates boilerplate code such as getters,
// setters, toString(), equals(), and hashCode() methods.
import lombok.Data;

//@NoArgsConstructor: Generates a no-argument constructor.
import lombok.NoArgsConstructor;

//@Id: Marks a field as the identifier for the document.
import org.springframework.data.annotation.Id;

//@Document: Indicates that this class is a MongoDB document.
import org.springframework.data.mongodb.core.mapping.Document;

//BigDecimal: A Java class used for high-precision arithmetic
//particularly useful for currency values.
import java.math.BigDecimal;

//This annotation tells Spring Data MongoDB that
// this class will be stored as a document in the MongoDB collection named "product".
//The "value" attribute specifies the name of the collection
// where instances of this model will be stored.
// If you omit this, the collection name will default to the class name in lowercase.
@Document(value = "product")

@Data // Generates the standard methods for the class (like getters and setters) based on its fields.
@AllArgsConstructor // Allows you to create a new Product object with all fields initialized.
@NoArgsConstructor // Allows you to create an empty Product object without needing to provide any values.
@Builder // Enables a builder pattern, making it easier to create instances of the class in a readable way.
public class Product {

    @Id
    private String id;

    private String name;
    private String description;
    private BigDecimal price;
}
