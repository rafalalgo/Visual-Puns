package kalambury.controller.game;

import javafx.scene.control.ColorPicker;
import kalambury.model.client.Client;
import kalambury.model.gui.*;

import java.util.regex.Pattern;

/**
 * Created by rafalbyczek on 15.06.16.
 */
public class TryAnswerController {
    public static void make_it(String word, ChatArea chatArea, DrawOption drawOption, ColorPicker colorPicker, Client client, AreaDraw areaDraw, TimeLineTask timeLineTask, TipArea tipArea, String aktDraw) {
        if (Pattern.matches(".*" + word + ".*", chatArea.getChatTextField().getText())) {
            word = ZgadnietoHasloController.make_it(
                    new Integer((int) (100 * (1 - drawOption.getProgressBar().getProgress()))),
                    word, client, areaDraw, timeLineTask, tipArea, aktDraw);
        }
    }
}
