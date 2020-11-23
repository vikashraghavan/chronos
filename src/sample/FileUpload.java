package sample;

import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class FileUpload implements Initializable {

    @FXML
    Label filename;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Initialized");
        new DerbyConn();
        //Derbyconn.createClassTable("test");
    }

    //Fetches the file path
    public void selectbtn(ActionEvent event) {
        FileChooser fc=new FileChooser();
        fc.setInitialDirectory(new File("V:\\"));
        File sltf=fc.showOpenDialog(null);
        if(sltf!=null){
            filename.setText("Uploaded successfully "+sltf.getName());
            ReadExcel.pth=sltf.getAbsolutePath();
        }else{
            filename.setText("Please try again");
        }
    }

    //calls the class to read the input file. Goto ReadExcel Class
    public void generatebtn(ActionEvent event) {
        new ReadExcel();
    }

}
