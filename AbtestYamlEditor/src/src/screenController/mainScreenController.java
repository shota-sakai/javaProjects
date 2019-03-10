package src.screenController;


import java.io.File;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import src.dto.AbtestUnit;
import src.factory.AbtestUnitFactory;
import src.manager.ScreenManager;
import src.util.AbtestInfoMaster;
import src.util.AbtestValidator;

public class mainScreenController implements Initializable {

	@FXML
	private Button inputButton;

    @FXML
    private Button selectButton;

    @FXML
    private TextField filePathField;

    @FXML
    private AnchorPane mainScreenPanel;

    @FXML
    private GridPane displayAbtestInfoArea;

    @FXML
    private ScrollPane displayMainPanel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	// テストコード
    	filePathField.setText("/Users/sakaisyota/doc/abtest.yaml");

    }

    /**
     * 「yamlファイル選択」を押下した時のイベントメソッド
     */
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

    /**
     *  「読み込み」ボタンを押下した時のイベントメソッド
     */
    @FXML
    public void onClickInputButton() {
    	try {
    		if( filePathField.getText() == "" ) {
    			return;
    		}
    		Path path = Paths.get(filePathField.getText());

    		List<String> inputString = Files.lines(path, StandardCharsets.UTF_8).collect(Collectors.toList());

    		// 読み込んだファイル内容をチェック
    		if( AbtestValidator.validateYamlFile(inputString) ) {
    			return;
    		}

    		List<AbtestUnit> abtestUnits = AbtestUnitFactory.create( inputString );
    		AbtestInfoMaster.getInstance().setList(abtestUnits);
    		List<String> abtestIdList = AbtestInfoMaster.getInstance().getList().stream()
    				.map(AbtestUnit::getId)
    				.collect(Collectors.toList());
    		displayAbtestInfoItemForGridPane(displayAbtestInfoArea, abtestIdList);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
    }

    /**
     * GridPaneにabテストの情報を表示する
     *
     * @param paneObject
     * @param abtestIdList
     */
    private void displayAbtestInfoItemForGridPane( GridPane paneObject, List<String> abtestIdList ) {
    	if( paneObject == null || CollectionUtils.isEmpty(abtestIdList) ) {
    		return;
    	}

    	for( int h=0; h<abtestIdList.size(); h++ ) {
    		final String itemId = String.valueOf(h);
    		Label id = new Label();
    		id.setText(abtestIdList.get(h));
    		paneObject.add(id, 0, 1+h);
    		Button editBtn = new Button("編集");
    		editBtn.setId(itemId);
    		editBtn.setOnAction((ActionEvent) ->{
    			int abtestIdNo = Integer.parseInt(itemId);
    			ScreenManager.getInstance().changeAbtestEditScreen(abtestIdNo);
    		});
    		paneObject.add(editBtn, 1, 1+h);

    		CheckBox delete = new CheckBox("削除");
    		delete.setId(itemId);
    		delete.setOnAction((ActionEvent) ->{
    			int index = Integer.parseInt(delete.getId());
    			AbtestInfoMaster.getInstance().getList().get(index).setDelete(delete.isSelected());
    		});

    		paneObject.add(delete, 2, 1+h);
    	}
    }
}
