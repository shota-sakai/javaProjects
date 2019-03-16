package src.manager;
import org.apache.commons.lang3.StringUtils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import src.screenController.AddIdScreenController;
import src.screenController.IdEditScreenController;
import src.screenController.InterfaceScreenEvent;

/**
 * スクリーン管理を提供するクラス
 *
 * @author sakaisyota
 *
 */
public class ScreenManager {

	private static ScreenManager instance;

	public static ScreenManager getInstance() {

		if( instance == null ) {
			instance = new ScreenManager();
		}
		return instance;
	}

	/**
	 * メイン画面を表示する
	 */
	public void displayMainScreen() {
		this.displayScreen(ScreenType.MAIN);
	}

	/**
	 * Abtest編集画面を表示する
	 */
	public void displayAbtestEditScreen(int abtestIdNo, InterfaceScreenEvent screenEvent) {

		try {
			// 画面切り替えに必要なfxml読み込み処理
			FXMLLoader loader = new FXMLLoader(getClass().getResource(ScreenType.ABTEST_EDIT.getFxmlPath()));
			Parent parent = loader.load();

			// abtest編集画面のコントローラクラスに必要な情報を譲渡
			final IdEditScreenController idEditScreenCtlr = (IdEditScreenController)loader.getController();
			idEditScreenCtlr.setupScreen(abtestIdNo, screenEvent);

			// ステージオブジェクトの生成と設定
			Scene abtestEditScene = new Scene(parent);
			if( StringUtils.isNotBlank(ScreenType.ABTEST_EDIT.getCssPath()) ) {
				abtestEditScene.getStylesheets().add(getClass().getResource(ScreenType.ABTEST_EDIT.getCssPath()).toExternalForm());
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
	 * Abtest追加画面を表示する
	 *
	 * @param screenEvent
	 */
	public void displayAbtestAddScreen( InterfaceScreenEvent screenEvent ) {

		try {
			// 画面切り替えに必要なfxml読み込み処理
			FXMLLoader loader = new FXMLLoader(getClass().getResource(ScreenType.ABTEST_ADD.getFxmlPath()));
			Parent parent = loader.load();

			// abtest追加画面のコントローラクラスに必要な情報を譲渡
			final AddIdScreenController addIdScreenCtlr = (AddIdScreenController)loader.getController();
			addIdScreenCtlr.setupScreen(screenEvent);

			// ステージオブジェクトの生成と設定
			Scene abtestEditScene = new Scene(parent);
			if( StringUtils.isNotBlank(ScreenType.ABTEST_ADD.getCssPath()) ) {
				abtestEditScene.getStylesheets().add(getClass().getResource(ScreenType.ABTEST_ADD.getCssPath()).toExternalForm());
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
	 * 現在のスクリーンを閉じる
	 *
	 * @param closeButton
	 */
	public void closeScreen( Button closeButton ) {
		closeButton.getScene().getWindow().fireEvent(
    			new WindowEvent(closeButton.getScene().getWindow(), WindowEvent.WINDOW_CLOSE_REQUEST));
	}

	/**
	 * 指定されたスクリーンを表示する
	 *
	 * @param screenInfo
	 */
	 private void displayScreen(ScreenType screenInfo) {
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