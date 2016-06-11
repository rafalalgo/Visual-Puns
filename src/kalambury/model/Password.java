package kalambury.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Michal Stobierski on 2016-06-08.
 */

public class Password {
    private static Random rNum = new Random();
    private static List<String> slowa = new ArrayList<>();

    static {

        final Path file = Paths.get("src/kalambury/model/slownik.txt");
        final Charset charset = Charset.forName("UTF-8");
        try (BufferedReader reader = Files.newBufferedReader(file, charset)) {

            String line = reader.readLine();

            int n = Integer.parseInt(line);

            while (n-- > 0) {
                line = reader.readLine();
                slowa.add(line);
            }

        } catch (final IOException x) {
            System.err.format("Nie znaleziono pliku z danymi - IOException: %s%n", x);
        }

    }

    public static String getWord() {
        return slowa.get(rNum.nextInt(slowa.size()));
    }
}