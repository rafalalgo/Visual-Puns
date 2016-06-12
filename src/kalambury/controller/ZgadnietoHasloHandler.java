package kalambury.controller;

import kalambury.database.Database;
import kalambury.model.*;

/**
 * Created by rafalbyczek on 12.06.16.
 */
public class ZgadnietoHasloHandler {
    public static String zgadnieto(String word, Client client, AreaDraw areaDraw, TimeLineTask timeLineTask, TipArea tipArea) {
        client.writeToServer("Użytkownik " + client.getName() + " zgadł hasło!");
        client.writeToServer(client.getName() + " + 10 punktów!");
        Integer punkty = new Integer(Database.instance.getPoint("SELECT punkty FROM ranking WHERE nazwa = '" + client.getName() + "';"));
        punkty += 10;
        Database.instance.deletePerson("DELETE FROM ranking WHERE nazwa = '" + client.getName() + "';");
        Database.instance.addPoint("INSERT INTO ranking(nazwa, punkty) VALUES('" + client.getName() + "'," + punkty.toString() + ")");
        word = Password.getWord(word);
        client.writeToServer("Nowa runda! Start!");
        areaDraw.getGraphicsContext2D().clearRect(0, 0, areaDraw.getCanvas().getWidth(), areaDraw.getCanvas().getHeight());
        timeLineTask.getTask().playFromStart();
        tipArea.getTip().setText("Podpowiedź: " + word);
        return word;
    }
}
