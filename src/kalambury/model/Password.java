package kalambury.model;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * Created by Michal Stobierski on 2016-06-08.
 */

public class Password {


    public static Random rNum = new Random();
    public static List<String> slowa = new ArrayList<>();
    public static Map<String, MyPair> slownik = new HashMap<>();

    static {

        final Path file = Paths.get("src/kalambury/model/slownik.txt");
        final Charset charset = Charset.forName("UTF-8");
        try (BufferedReader reader = Files.newBufferedReader(file, charset)) {

            String line = reader.readLine();
            int n = Integer.parseInt(line);

            while (n-- > 0) {
                line = reader.readLine();

                String[] data = line.split("[|]");
                List<String> tmpList = new ArrayList<>();
                int tmpIt = 0;
                for (String S : data) {
                    if (tmpIt++ == 0) continue;
                    tmpList.add(S);
                }
                MyPair noweHaslo = new MyPair(tmpList);
                slownik.put(data[0], noweHaslo);
            }

        } catch (final IOException x) {
            System.err.format("Nie znaleziono pliku z danymi - IOException: %s%n", x);
        }

    }

    public static String getWord(String word) {
        return slowa.get(rNum.nextInt(slowa.size()));
    }

    public static String getHint(String haslo) {
        return slownik.get(haslo).get();
    }
}
