package kalambury.controller;

import javafx.scene.control.ColorPicker;
import kalambury.database.Database;
import kalambury.model.*;

/**
 * Created by rafalbyczek on 12.06.16.
 */
public class ZgadnietoHasloController {
    public static String zgadnieto(DrawOption drawOption, ColorPicker colorPicker, Integer ADD, String word, Client client, AreaDraw areaDraw, TimeLineTask timeLineTask, TipArea tipArea, String aktDraw) {
        client.writeToServer("Użytkownik " + client.getName() + " zgadł hasło!");
        client.writeToServer(client.getName() + " + " + ADD.toString() + "!");
        Integer punkty = new Integer(Database.instance.getPoint("SELECT punkty FROM ranking WHERE nazwa = '" + client.getName() + "';"));
        punkty += ADD;
        Database.instance.deletePerson("DELETE FROM ranking WHERE nazwa = '" + client.getName() + "';");
        Database.instance.addPoint("INSERT INTO ranking(nazwa, punkty) VALUES('" + client.getName() + "'," + punkty.toString() + ")");
        word = Password.getWord(word);
        client.writeToServer("Nowa runda! Start!");

        String kto = Database.instance.getWord("SELECT name FROM gracze WHERE rysuje = 1");
        Integer ile = Database.instance.getPoint("SELECT ile_razy FROM gracze WHERE rysuje = 1");
        Database.instance.deletePerson("DELETE FROM gracze WHERE rysuje = 1");
        Database.instance.addPoint("INSERT INTO gracze(name, ile_razy, rysuje) VALUES('" + kto + "', " + new Integer(ile + 1).toString() + ", 0)");

        kto = Database.instance.getWord("SELECT min(name) FROM gracze WHERE ile_razy = (SELECT min(ile_razy) FROM gracze);");
        ile = Database.instance.getPoint("SELECT ile_razy FROM gracze WHERE ile_razy = (SELECT min(ile_razy) FROM gracze) AND name = '" + kto + "'");
        Database.instance.deletePerson("DELETE FROM gracze WHERE name = '" + kto + "'");
        Database.instance.addPoint("INSERT INTO gracze(name, ile_razy, rysuje) VALUES('" + kto + "', " + ile.toString() + ", 1)");

        client.writeToServer("Teraz rysuje " + kto);


        if(client.getName().equals(aktDraw)) {
            areaDraw.getGraphicsContext2D().clearRect(0, 0, areaDraw.getCanvas().getWidth(), areaDraw.getCanvas().getHeight());
        }

        Database.instance.changeTime("DELETE FROM czas;");
        Database.instance.changeTime("INSERT INTO czas(czas) VALUES ('0')");

        timeLineTask.getTask().playFromStart();
        tipArea.getTip().setText("Podpowiedź: " + Password.getHint(word));
        return word;
    }
}
