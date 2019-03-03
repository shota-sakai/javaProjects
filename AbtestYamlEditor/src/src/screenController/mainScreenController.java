package src.screenController;


import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import src.dto.AbtestUnit;
import src.factory.AbtestUnitFactory;
import src.util.AbtestValidator;

public class mainScreenController {

	@FXML
	private Button inputButton;

    @FXML
    private Button selectButton;

    @FXML
    private TextField filePathField;

    @FXML
    private AnchorPane mainScreenPanel;

    @FXML
    private GridPane displayAtestInfoArea;

    @FXML
    public void onClickSelectButton() {
    	FileChooser fc = new FileChooser();
    	fc.setTitle("yamlファイル選択");
    	fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("yamlファイル", "*.yaml"));
    	File file = fc.showOpenDialog(null);

    	if( file != null ) {
    		filePathField.setText(file.getPath());
    	}
    }

    @FXML
    public void onClickInputButton() {
    	try {
    		Path path = Paths.get(filePathField.getText());
    		List<String> inputString = Files.lines(path, StandardCharsets.UTF_8).collect(Collectors.toList());

    		// 読み込んだファイル内容をチェック
    		if( AbtestValidator.validateYamlFile(inputString) ) {
    			return;
    		}

    		List<AbtestUnit> abtestUnits = AbtestUnitFactory.create( inputString );

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
    }
}
