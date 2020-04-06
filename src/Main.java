import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage stage) throws Exception {

        //EntityManager em = DBManager.getInstance().getEntityManger();

        stage.setHeight(800);
        stage.setWidth(1280);
        stage.setResizable(false);
        stage.setTitle("BubbleWorkout");
        stage.getIcons().add(new Image("/include/weight.png"));

        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/login.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }
}
