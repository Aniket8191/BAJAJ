import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Main {
    public static void main(String[] args) {
        try {
            // Read JSON file
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(new File("input.json"));

            // Extract values
            String firstName = rootNode.path("student").path("first_name").asText().toLowerCase().replace(" ", "");
            String rollNumber = rootNode.path("student").path("roll_number").asText().toLowerCase().replace(" ", "");

            // Concatenate values
            String concatenatedString = firstName + rollNumber;

            // Generate MD5 hash
            String hash = getMD5Hash(concatenatedString);

            // Write hash to output.txt
            FileWriter writer = new FileWriter("output.txt");
            writer.write(hash);
            writer.close();

            System.out.println("MD5 Hash written to output.txt: " + hash);

        } catch (IOException e) {
            System.out.println("Error reading JSON file: " + e.getMessage());
        }
    }

    public static String getMD5Hash(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : messageDigest) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
