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
import src.util.AbtestUnitMaster;
import src.util.AbtestYamlValidator;

public class mainScreenController implements Initializable, InterfaceScreenEvent {

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

    @FXML
    private Button outputButton;

    @FXML
    private Button addTestIdButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	// テストコード
    	filePathField.setText("/Users/sakaisyota/doc/abtest.yaml");

		// 「ABテストID追加」「出力」ボタンを非表示
    	addTestIdButton.setVisible(false);
    	outputButton.setVisible(false);
    }

    /**
     * 「...」を押下した時のイベントメソッド
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
    		if( AbtestYamlValidator.validateYamlFile(inputString) ) {
    			return;
    		}

    		// 読み込んだyamlファイルの文字列からabtestオブジェクトのリストを生成
    		List<AbtestUnit> abtestUnits = AbtestUnitFactory.create( inputString );

    		// AbtestUnitマスタに設定
    		AbtestUnitMaster.getInstance().setList(abtestUnits);

    		// AbtestIdを抽出
    		List<String> abtestIdList = AbtestUnitMaster.getInstance().getList().stream()
    				.map(AbtestUnit::getId)
    				.collect(Collectors.toList());

        	// 現在表示しているAbtest情報を全て削除
    		displayAbtestInfoArea.getChildren().clear();

    		// AbtestId一覧を画面に表示
    		displayAbtestInfoItemForGridPane(this.displayAbtestInfoArea, abtestIdList);

    		// 「ABテストID追加」「出力」ボタンを表示
    		addTestIdButton.setVisible(true);
    		outputButton.setVisible(true);

		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    /**
     * 「ABテストID追加」ボタンを押下した時のイベント処理
     */
    @FXML
    public void onClickAddTestIdButton() {
    	ScreenManager.getInstance().displayAbtestAddScreen(this);
    }

    /**
     * 「出力」ボタンを押下した時のイベント処理
     */
    @FXML
    public void onClickOutputButton() {

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
    			ScreenManager.getInstance().displayAbtestEditScreen(abtestIdNo, this);
    		});
    		paneObject.add(editBtn, 1, 1+h);

    		CheckBox delete = new CheckBox("削除");
    		delete.setId(itemId);
    		delete.setOnAction((ActionEvent) ->{
    			int index = Integer.parseInt(delete.getId());
    			AbtestUnitMaster.getInstance().getList().get(index).setDelete(delete.isSelected());
    		});
    		paneObject.add(delete, 2, 1+h);
    	}
    }

    /**
     * 表示しているAbtest情報を更新して再表示する
     *
     * @param abtestUnitList
     * @param displayPanel
     */
    private void redisplayInfoItemForGridPane( GridPane paneObject, List<String> abtestIdList ) {

    	// 引数が不正な場合は抜ける
    	if( paneObject == null || CollectionUtils.isEmpty(abtestIdList) ) {
    		return;
    	}

    	// 現在表示しているAbtest情報を全て削除
    	paneObject.getChildren().clear();

    	// 引数のabtest情報を元に再表示
    	displayAbtestInfoItemForGridPane( paneObject, abtestIdList );
    }

	@Override
	public void init() {
		// TODO 自動生成されたメソッド・スタブ
	}

	@Override
	public void update() {

		List<String> abtestIdList = AbtestUnitMaster.getInstance().getList().stream()
				.map(AbtestUnit::getId)
				.collect(Collectors.toList());
		// AbtestUnitマスタ情報を元に表示しているAbtest情報を更新する
		redisplayInfoItemForGridPane(this.displayAbtestInfoArea, abtestIdList);
	}

	@Override
	public void distract() {
		// TODO 自動生成されたメソッド・スタブ
	}
}
