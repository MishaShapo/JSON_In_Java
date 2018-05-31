import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

@JsonInclude (JsonInclude.Include.NON_NULL)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Book {

    String id;
    List<String> cat;
    String name;
    String author;
    String series_t;
    int sequence_i;
    String genre_s;
    boolean inStock;
    double price;
    int pages_i;

    public static void main(String args[])
            throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Book book = mapper.readValue(new File("../resources/book_input.json"), Book.class);

        mapper.writeValue(new File("../resources/book_output.json"), book);
        System.out.println(book);
    }

}
