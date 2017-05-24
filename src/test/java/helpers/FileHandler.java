package helpers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public abstract class FileHandler {

    public static String fileReader(String fileName) throws IOException {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            StringBuilder stringBuilder = new StringBuilder();
            String line = bufferedReader.readLine();

            while (line != null) {
                stringBuilder.append(line);
                stringBuilder.append("\n");
                line = bufferedReader.readLine();
            }
            return stringBuilder.toString();
        }
    }
}
