import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.File;
import java.io.IOException;
import java.rmi.server.ExportException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

@JsonSerialize (using = Library.LibrarySerializer.class)
@JsonDeserialize(using = Library.LibraryDeserializer.class)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Library {

    private List<Book> books;

    public Library(Book[] books){
        this.books = Arrays.asList(books);
    }

    public static void main(String[] args)
            throws IOException {
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            Book[] books = mapper.readValue(new File("../resources/library_input.json"), Book[].class);

            Library lib = new Library(books);
            mapper.writeValue(new File("../resources/library_output.json"), lib);
            System.out.println(lib);
        }

    protected static class LibrarySerializer extends JsonSerializer<Library> {

        public void serialize(Library library, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
                throws IOException, JsonProcessingException {
                jsonGenerator.writeStartArray();
                for(Book b : library.books){
                    jsonGenerator.writeObject(b);
                }
                jsonGenerator.writeEndArray();
        }
    }

    protected static class LibraryDeserializer extends JsonDeserializer<Library> {
        public Library deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
                throws IOException, JsonProcessingException {
            Iterator<Book[]> iter = jsonParser.readValuesAs(Book[].class);
            if(!iter.hasNext()) {
                throw new IOException("Input file for Library doesn't have an array of Books");
            }
            return new Library(iter.next());
        }
    }
}
