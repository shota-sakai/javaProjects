package src.screenController;

import java.util.List;

import com.google.common.collect.Lists;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.WindowEvent;

public class IdEditScreenController {

	@FXML
	private AnchorPane basePanel;

	@FXML
	private TextField inputTestId;

	@FXML
    private TextField inputVersion;

    @FXML
    private TextField inputPatternId_1;

    @FXML
    private TextField inputPatternId_2;

    @FXML
    private TextField inputPatternId_3;

    @FXML
    private TextField inputPatternId_4;

    @FXML
    private TextField inputPatternId_5;

    @FXML
    private TextField inputRatio_1;

    @FXML
    private TextField inputRatio_2;

    @FXML
    private TextField inputRatio_3;

    @FXML
    private TextField inputRatio_4;

    @FXML
    private TextField inputRatio_5;

    @FXML
    private Button completeButton;

    @FXML
    private Button cancelButton;

    /** 編集を行うABテストIDのインデックス */
    private int abtestIdNo = -1;

    /** ABテストケースの表示・入力用GUIオブジェクトリスト */
    private List<caseGUIObject> caseObjList = Lists.newArrayList();

    /** caseGUIオブジェクトの表示開始Y座標 */
    private static final double START_LAYOUT_Y = 115;

    /** caseGUIオブジェクトを表示する際のY座標オフセット値 */
    private static final double OFFSET_CASE_ITEM_LAYOUT_Y = 40;

    /**
     * 「キャンセル」ボタンを押下した時のイベントメソッド
     */
    @FXML
    public void onClickCancelButton() {
    	// AbtestEditスクリーンを閉じる
    	cancelButton.getScene().getWindow().fireEvent(
    			new WindowEvent(cancelButton.getScene().getWindow(), WindowEvent.WINDOW_CLOSE_REQUEST));
    }

    /**
     * 「完了」ボタンを押下した時のイベントメソッド
     */
    @FXML
    public void onClickCompleteButton() {
    	// TODO 次回実装
    }

    /**
     * 画面表示準備処理
     */
    public void setupScreen() {
    	if(  this.abtestIdNo == -1 ) {
    		return;
    	}
    	// TODO 次回実装
    }

	public int getAbtestIdNo() {
		return abtestIdNo;
	}

	public void setAbtestIdNo(int abtestIdNo) {
		this.abtestIdNo = abtestIdNo;
	}

	class caseGUIObject {

		/** 「patternIdName」オブジェクトの設定値 */
		static final double PATTERN_ID_NAME_LAYOUT_X = 36;
		static final double PATTERN_ID_NAME_WIDTH = 43;
		static final double PATTERN_ID_NAME_HEIGHT = 27;

		/** 「inputPatternId」オブジェクトの設定値 */
		static final double INPUT_PATTERN_ID_LAYOUT_X = 95;
		static final double INPUT_PATTERN_ID_WIDTH = 62;
		static final double INPUT_PATTERN_ID_HEIGHT = 27;

		/** 「inputRatio」オブジェクトの設定値 */
		static final double INPUT_RATIO_LAYOUT_X = 185;
		static final double INPUT_RATIO_WIDTH = 50;
		static final double INPUT_RATIO_HEIGHT = 27;

		/** 「percentLabel」オブジェクトの設定値 */
		static final double PERCENT_LABEL_LAYOUT_X = 240;
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
			this.inputPatternId.setLayoutX(INPUT_PATTERN_ID_LAYOUT_X);
			this.inputPatternId.setLayoutY(layoutHeight);
			this.inputPatternId.setPrefWidth(INPUT_PATTERN_ID_WIDTH);
			this.inputPatternId.setPrefHeight(INPUT_PATTERN_ID_HEIGHT);

			this.inputRatio = new TextField(inputRatio);
			this.inputRatio.setLayoutX(INPUT_RATIO_LAYOUT_X);
			this.inputRatio.setLayoutY(layoutHeight);
			this.inputRatio.setPrefWidth(INPUT_RATIO_WIDTH);
			this.inputRatio.setPrefHeight(INPUT_PATTERN_ID_HEIGHT);

			this.percentLabel = new Label("%");
			this.percentLabel.setLayoutX(PERCENT_LABEL_LAYOUT_X);
			this.percentLabel.setLayoutY(layoutHeight);
			this.percentLabel.setPrefWidth(PERCENT_LABEL_WIDTH);
			this.percentLabel.setPrefHeight(PERCENT_LABEL_HEIGHT);
		}
	}
}
