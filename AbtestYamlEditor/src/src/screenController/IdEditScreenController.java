package src.screenController;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Lists;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import src.dto.AbtestCaseUnit;
import src.dto.AbtestUnit;
import src.manager.ScreenManager;
import src.util.AbtestUnitMaster;
import src.util.AbtestUnitValidator;

/**
 * AbtestId編集画面コントローラクラス
 *
 * @author sakaisyota
 *
 */
public class IdEditScreenController implements InterfaceScreenEvent{

	@FXML
	private AnchorPane basePanel;

	@FXML
	private Label errorMessageLabel;

	@FXML
	private TextField inputTestId;

	@FXML
    private TextField inputVersion;

    @FXML
    private Button completeButton;

    @FXML
    private Button cancelButton;

    /** 編集を行うABテストIDのインデックス */
    private int abtestIdNo = -1;

    /** ABテストケースの表示・入力用GUIオブジェクトリスト */
    private List<caseGUIObject> caseObjList = Lists.newArrayList();

    /** caseGUIオブジェクトの表示開始Y座標 */
    private static final double START_LAYOUT_Y = 80;

    /** caseGUIオブジェクトを表示する際のY座標オフセット値 */
    private static final double OFFSET_CASE_ITEM_LAYOUT_Y = 40;

    /** 遷移元スクリーンのイベント */
    private InterfaceScreenEvent previousScreen;

	/**
     * 「キャンセル」ボタンを押下した時のイベントメソッド
     */
    @FXML
    public void onClickCancelButton() {
    	// AbtestEditスクリーンを閉じる
    	ScreenManager.getInstance().closeScreen(cancelButton);
    }

    /**
     * 「完了」ボタンを押下した時のイベントメソッド
     */
    @FXML
    public void onClickCompleteButton() {

    	// 入力値のバリデーションチェック実施
    	if( validationInputValue( inputTestId.getText(), inputVersion.getText(), caseObjList, errorMessageLabel ) ) {
    		return;
    	}

    	// 入力されたabtest情報からabtestUnitオブジェクトを生成
    	AbtestUnit editAbtestUnit =  createEditAbtestUnit();

    	// abtestUnitオブジェクトをAbtestUnitマスタを更新する
    	AbtestUnitMaster.getInstance().updateAbtestUnit( this.abtestIdNo, editAbtestUnit );

    	// AbtestEdit画面を閉じる
    	ScreenManager.getInstance().closeScreen(cancelButton);

    	// 遷移元の画面の更新処理実施
    	this.previousScreen.update();
    }

    /**
     * 入力値のバリデーションチェックを行う
     *
     * @param testId テストID
     * @param version バージョン
     * @param caseObjList ケースIDとケース割合のリスト
     * @param errorMessageLabel エラーメッセージ表示用ラベルオブジェクト
     * @return 入力値が不正な場合は true を返す
     */
    private boolean validationInputValue(
    		String testId,
    		String version,
    		List<caseGUIObject> caseObjList,
			Label errorMessageLabel) {

    	// テストID
    	if( AbtestUnitValidator.validationTestId(testId) ) {
    		errorMessageLabel.setText(AbtestUnitValidator.ERROR_MSG_TEST_ID);
    		return true;
    	}

    	// バージョン
    	if( AbtestUnitValidator.validationVersion(version) ) {
    		errorMessageLabel.setText(AbtestUnitValidator.ERROR_MSG_VERSION);
    		return true;
    	}

    	for( caseGUIObject caseObj : caseObjList ) {
    		// ケースID
    		if( AbtestUnitValidator.validationCaseId(caseObj.inputPatternId.getText()) ) {
    			errorMessageLabel.setText(AbtestUnitValidator.ERROR_MSG_CASE_ID);
    			return true;
    		}
    		// ケース割合
    		if( AbtestUnitValidator.validationRatio(caseObj.inputRatio.getText()) ) {
    			errorMessageLabel.setText(AbtestUnitValidator.ERROR_MSG_RATIO);
    			return true;
    		}
    	}

    	int ratioTotal = caseObjList.stream()
    			.map( caseObj -> caseObj.inputRatio.getText() )
    			.filter( r -> StringUtils.isNumeric(r) )
    			.mapToInt(Integer::parseInt)
    			.sum();

    	// ケース割合の合計値
    	if( ratioTotal != 100 ) {
    		errorMessageLabel.setText(AbtestUnitValidator.ERROR_MSG_RATIO_TOTAL);
    		return true;
    	}

		return false;
	}

	/**
     * 編集画面の入力されたabtest情報からAbtestUnitを生成する
     *
     * @return
     */
	private AbtestUnit createEditAbtestUnit() {
		AbtestUnit editAbtest = new AbtestUnit();
    	String id = inputTestId.getText();
    	editAbtest.setId(id);

    	String version = inputVersion.getText();
    	editAbtest.setVersion(Integer.parseInt(version));

    	List<AbtestCaseUnit> caseUnitList = Lists.newArrayList();
    	for( caseGUIObject caseObj : caseObjList ) {
    		AbtestCaseUnit caseUnit = new AbtestCaseUnit();
    		caseUnit.setCaseId(caseObj.inputPatternId.getText());
    		String ratio = caseObj.inputRatio.getText();
    		caseUnit.setRatio(Integer.parseInt(ratio));
    		caseUnitList.add(caseUnit);
    	}
    	editAbtest.setCaseUnits(caseUnitList);

    	return editAbtest;
	}

    /**
     * 画面表示準備処理
     */
    public void setupScreen(int abtestIdNo, InterfaceScreenEvent previousScreen) {

    	// 引数が不正な場合は抜ける
    	if(  abtestIdNo == -1 || previousScreen == null ) {
    		return;
    	}

    	this.abtestIdNo = abtestIdNo;
    	this.previousScreen = previousScreen;

    	// Abtest情報を取得してGUIオブジェクトに設定
    	AbtestUnit targetUnit =  AbtestUnitMaster.getInstance().getList().get(abtestIdNo);
    	inputTestId.setText(targetUnit.getId());getClass();
    	String version = String.valueOf(targetUnit.getVersion());
    	inputVersion.setText(version);

    	int caseNo = 1;
    	for( AbtestCaseUnit caseUnit : targetUnit.getCaseUnits() ) {
    		String patternId = caseUnit.getCaseId();
    		String ratio = String.valueOf(caseUnit.getRatio());
    		double layoutY = START_LAYOUT_Y + (OFFSET_CASE_ITEM_LAYOUT_Y*caseNo);
    		caseGUIObject caseObj = new caseGUIObject("Case_" + caseNo, patternId, ratio, layoutY);
    		caseObj.addScreeen(basePanel);
    		caseObjList.add(caseObj);
    		caseNo++;
    	}
    }

	@Override
	public void init() {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void update() {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void distract() {
		if( this.previousScreen == null ) {
			return;
		}
		// 遷移元の画面(メイン画面)のAbtest表示を更新する
		this.previousScreen.update();
	}

	/**
	 * Abtestの1ケースを表示するために必要なGUIオブジェクトを持ったクラス
	 *
	 * @author sakaisyota
	 *
	 */
	class caseGUIObject {

		/** 「patternIdName」オブジェクトの設定値 */
		static final double PATTERN_ID_NAME_LAYOUT_X = 36;
		static final double PATTERN_ID_NAME_WIDTH = 45;
		static final double PATTERN_ID_NAME_HEIGHT = 27;

		/** 「inputCaseId」オブジェクトの設定値 */
		static final double INPUT_CASE_ID_LAYOUT_X = 95;
		static final double INPUT_CASE_ID_WIDTH = 65;
		static final double INPUT_CASE_ID_HEIGHT = 27;

		/** 「inputRatio」オブジェクトの設定値 */
		static final double INPUT_RATIO_LAYOUT_X = 175;
		static final double INPUT_RATIO_WIDTH = 45;
		static final double INPUT_RATIO_HEIGHT = 27;

		/** 「percentLabel」オブジェクトの設定値 */
		static final double PERCENT_LABEL_LAYOUT_X = 230;
		static final double PERCENT_LABEL_WIDTH = 11;
		static final double PERCENT_LABEL_HEIGHT = 27;

		/** case名表示ラベル */
		Label patternIdName;
		/** case入力用テキストフィールド */
		TextField inputPatternId;
		/** 割合入力用テキストフィールド */
		TextField inputRatio;
		/** 「%」表示ラベル  */
		Label percentLabel;

		/**
		 * コンストラクタ
		 *
		 * @param patternIdName
		 * @param inputPatternId
		 * @param inputRatio
		 * @param layoutHeight
		 */
		public caseGUIObject( String patternIdName, String inputPatternId, String inputRatio, double layoutHeight ) {
			this.patternIdName = new Label(patternIdName);
			this.patternIdName.setLayoutX(PATTERN_ID_NAME_LAYOUT_X);
			this.patternIdName.setLayoutY(layoutHeight);
			this.patternIdName.setPrefWidth(PATTERN_ID_NAME_WIDTH);
			this.patternIdName.setPrefHeight(PATTERN_ID_NAME_HEIGHT);

			this.inputPatternId = new TextField(inputPatternId);
			this.inputPatternId.setLayoutX(INPUT_CASE_ID_LAYOUT_X);
			this.inputPatternId.setLayoutY(layoutHeight);
			this.inputPatternId.setPrefWidth(INPUT_CASE_ID_WIDTH);
			this.inputPatternId.setPrefHeight(INPUT_CASE_ID_HEIGHT);

			this.inputRatio = new TextField(inputRatio);
			this.inputRatio.setLayoutX(INPUT_RATIO_LAYOUT_X);
			this.inputRatio.setLayoutY(layoutHeight);
			this.inputRatio.setPrefWidth(INPUT_RATIO_WIDTH);
			this.inputRatio.setPrefHeight(INPUT_RATIO_HEIGHT);

			this.percentLabel = new Label("%");
			this.percentLabel.setLayoutX(PERCENT_LABEL_LAYOUT_X);
			this.percentLabel.setLayoutY(layoutHeight);
			this.percentLabel.setPrefWidth(PERCENT_LABEL_WIDTH);
			this.percentLabel.setPrefHeight(PERCENT_LABEL_HEIGHT);
		}

		public void addScreeen( AnchorPane basePanel ) {
			basePanel.getChildren().add(this.patternIdName);
			basePanel.getChildren().add(inputPatternId);
			basePanel.getChildren().add(inputRatio);
			basePanel.getChildren().add(percentLabel);
		}
	}

}
