package View;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class IndexPage extends Stage {

    private Scene indexPageScene; // scene as attribute

    public IndexPage() throws IOException {
        
        //load adminDachboard FXML File in adminDachboard Scene
        FXMLLoader indexLoader = new FXMLLoader(getClass().getResource("/View/Admin/index.fxml"));
        Parent indexRoot = indexLoader.load();
        indexPageScene = new Scene(indexRoot);
        this.setScene(indexPageScene);
        this.setTitle("Home Page");
        this.show();

    }

}
