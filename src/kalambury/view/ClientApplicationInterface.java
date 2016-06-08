package kalambury.view;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * Created by rafalbyczek on 08.06.16.
 */
public interface ClientApplicationInterface {
    static Color getColor() {
        return ClientApplication.color;
    }

    static double getStartOpacity() {
        return ClientApplication.START_OPACITY;
    }

    void update();

    void addPoint(String name, Integer punkty);

    int getPunkty();

    void setPunkty(int punkty);

    TextField getChatTextField();

    void setChatTextField(TextField chatTextField);

    ListView<String> getChatListView();

    void setChatListView(ListView<String> chatListView);

    double getCurrentOpacity();

    void setCurrentOpacity(double currentOpacity);

    double getStrokeWidth();

    void setStrokeWidth(double strokeWidth);

    Client getClient();

    void setClient(Client client);

    Scene makeInitScene(Stage primaryStage);

    MenuBar getMb();

    void setMb(MenuBar mb);

    ArrayList<Thread> getThreads();

    void setThreads(ArrayList<Thread> threads);

    Canvas getCanvas();

    void setCanvas(Canvas canvas);

    void handleMousePressed(GraphicsContext gc, MouseEvent e);

    void handleMouseReleased(GraphicsContext gc, MouseEvent e);

    void handleMouseDragged(GraphicsContext gc, MouseEvent e);
}
