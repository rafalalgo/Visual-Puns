package kalambury.model.dictionary;

import kalambury.database.Database;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * Created by rafalbyczek on 31.05.16.
 */

public class Password {
    public static Random rNum = new Random();
    public static List<String> slowa = new ArrayList<>();
    public static Map<String, MyPair> slownik = new HashMap<>();

    static {
        final Path file = Paths.get("src/kalambury/model/dictionary/slownik.txt");
        final Charset charset = Charset.forName("UTF-8");
        try (BufferedReader reader = Files.newBufferedReader(file, charset)) {
            String line = reader.readLine();
            while (line != null) {
                String[] data = line.split("[|]");
                List<String> tmpList = new ArrayList<>();
                int tmpIt = 0;
                for (String S : data) {
                    if (tmpIt++ == 0) continue;
                    tmpList.add(S);
                }
                MyPair noweHaslo = new MyPair(tmpList);
                slownik.put(data[0], noweHaslo);
                slowa.add(data[0]);
                line = reader.readLine();
            }
            reader.close();
        } catch (final IOException x) {
            System.err.format("Nie znaleziono pliku z danymi - IOException: %s%n", x);
        }

    }

    public static String getWord(String word) {
        Database.instance.changeWord("DELETE FROM slowo WHERE slowo.slowo = '" + word + "'");
        Database.instance.changeWord("INSERT INTO slowo(slowo) VALUES('" + slowa.get(rNum.nextInt(slowa.size())) + "')");
        return Database.instance.getWord("SELECT slowo FROM slowo LIMIT 1;");
    }

    public static String getHint(String haslo) {
        return slownik.get(haslo).get();
    }
}
