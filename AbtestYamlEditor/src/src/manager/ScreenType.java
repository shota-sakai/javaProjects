package src.manager;

/**
 * アプケーションの画面の種類を表現するenum
 *
 * @author sakaisyota
 *
 */
public enum ScreenType {

	/** メイン画面 */
	MAIN("Main", "/application/Main.fxml", "/application/application.css"),
	/** ABtest編集画面 */
	ABTEST_EDIT("ABTEST_EDIT", "/application/EditAbtest.fxml", ""),
	/** Abtest追加画面 */
	ABTEST_ADD("ABTEST_ADD","/application/AddAbtest.fxml","");

	/** スクリーン名 */
	private String name;
	/** fxmlファイルパス */
	private String fxmlPath;
	/** cssファイルパス */
	private String cssPath;

	private ScreenType(String name, String fxmlPath, String cssPath ) {
		this.name = name;
		this.fxmlPath = fxmlPath;
		this.cssPath = cssPath;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFxmlPath() {
		return fxmlPath;
	}

	public String getCssPath() {
		return cssPath;
	}
}
