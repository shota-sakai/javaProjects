package src.manager;
import org.apache.commons.lang3.StringUtils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import src.screenController.IdEditScreenController;

/**
 * スクリーンの切り替え機能を提供するクラス
 *
 * @author sakaisyota
 *
 */
public class ScreenManager {

	private static ScreenManager instance;

	private ScreenManager() {}

	public static ScreenManager getInstance() {

		if( instance == null ) {
			instance = new ScreenManager();
		}
		return instance;
	}

	/**
	 * メイン画面に切り替える
	 */
	public void changeMainScreen() {
		this.changeScreen(ScreenKind.MAIN);
	}

	/**
	 * Abtest編集画面に切り替える
	 */
	public void changeAbtestEditScreen(int abtestIdNo) {
		try {
			// 画面切り替えに必要なfxml読み込み処理
			FXMLLoader loader = new FXMLLoader(getClass().getResource(ScreenKind.ABTEST_EDIT.getFxmlPath()));
			Parent parent = loader.load();

			// abtest編集画面のコントローラクラスに必要な情報を譲渡
			final IdEditScreenController idEditScreenCtlr = (IdEditScreenController)loader.getController();
			idEditScreenCtlr.setAbtestIdNo(abtestIdNo);
			idEditScreenCtlr.setupScreen();

			// ステージオブジェクトの生成と設定
			Scene abtestEditScene = new Scene(parent);
			if( StringUtils.isNotBlank(ScreenKind.ABTEST_EDIT.getCssPath()) ) {
				abtestEditScene.getStylesheets().add(getClass().getResource(ScreenKind.ABTEST_EDIT.getCssPath()).toExternalForm());
			}
			Stage abtestEditStage = new Stage();

			// 遷移元の画面がアクティブにならないように設定
			abtestEditStage.initModality(Modality.APPLICATION_MODAL);

			abtestEditStage.setScene(abtestEditScene);
			abtestEditStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * screenを切り替える
	 *
	 * @param screenInfo
	 */
	 private void changeScreen(ScreenKind screenInfo) {
		if( screenInfo == null ) {
			return;
		}

		try {
			// 画面切り替えに必要なfxml読み込み処理
			Parent parent = FXMLLoader.load(getClass().getResource(screenInfo.getFxmlPath()));

			// ステージオブジェクトの生成と設定
			Scene nextScene = new Scene(parent);
			if( StringUtils.isNotBlank(screenInfo.getCssPath()) ) {
				nextScene.getStylesheets().add(getClass().getResource(screenInfo.getCssPath()).toExternalForm());
			}
			Stage nextStage = new Stage();

			// 遷移元の画面がアクティブにならないように設定
			nextStage.initModality(Modality.APPLICATION_MODAL);

			nextStage.setScene(nextScene);
			nextStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}