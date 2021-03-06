package ru.overprof.pyatnashki;

import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.input.touch.TouchEvent;

import android.view.KeyEvent;

public class MainState extends Scene {
	
	public static MainMenuScene mainMenuScene_ = new MainMenuScene(1);
	public static GameScene gameScene_ = new GameScene(1);
	public static SettingsScene settingsScene_ = new SettingsScene(1);
	public static RecordsScene recordsScene_ = new RecordsScene(1);
	
	 //private static int gameState_;
	public static GAMESTATUS gameStatus_;
	
	public MainState(int pLayerCount) {
		super(pLayerCount);
		attachChild(mainMenuScene_);
		attachChild(gameScene_);
		attachChild(settingsScene_);
		attachChild(recordsScene_);
		
		ShowMainMenu();
		gameStatus_ = GAMESTATUS.MainMenuStatus; //?
		
	}
	
	public static void ShowGameScene() {
		gameScene_.Show();
		mainMenuScene_.Hide();
		settingsScene_.Hide();
		recordsScene_.Hide();
		gameStatus_ = GAMESTATUS.GamePlayingStatus;
		gameScene_.gamePaused_ = false;
	}
	
	public static void ShowMainMenu() {
		mainMenuScene_.Show();
		gameScene_.Hide();
		settingsScene_.Hide();
		recordsScene_.Hide();
		gameStatus_ = GAMESTATUS.MainMenuStatus;
	}
	
	public static void ShowSettings() {
		settingsScene_.Show();
		mainMenuScene_.Hide();
		gameScene_.Hide();
		recordsScene_.Hide();
		gameStatus_ = GAMESTATUS.SettingsStatus;
	}
	
	public static void ShowRecords() {
		recordsScene_.Update();
		recordsScene_.Show();
		settingsScene_.Hide();
		mainMenuScene_.Hide();
		gameScene_.Hide();
		gameStatus_ = GAMESTATUS.RecordsStatus;
	}
	
	@Override
	public boolean onSceneTouchEvent(TouchEvent pSceneTouchEvent) {
		
		switch (gameStatus_) 
		{		
		case MainMenuStatus:
			mainMenuScene_.onSceneTouchEvent(pSceneTouchEvent);
			break;
		case GamePlayingStatus:	
			gameScene_.onSceneTouchEvent(pSceneTouchEvent);
			break;
		case SettingsStatus:	
			settingsScene_.onSceneTouchEvent(pSceneTouchEvent);
			break;			
		default:
			break;
		}
		//return super.onSceneTouchEvent(pSceneTouchEvent);
		return true;
	}
	
	
	enum GAMESTATUS{
		MainMenuStatus,
		SelectLevelsStatus,
		GamePlayingStatus,
		SettingsStatus,
		RecordsStatus;
	}


	public void KeyPressed(int keyCode, KeyEvent event) {
		switch (gameStatus_) 
		{		
		case MainMenuStatus:
			PyatnashkiActivity.main_.onDestroy();
			break;
		case GamePlayingStatus:	
			ShowMainMenu();
			gameScene_.startActions_ = true;
			gameScene_.gamePaused_ = true;
			break;
		case SettingsStatus:
			settingsScene_.SaveSettings();
			ShowMainMenu();
			break;
		case RecordsStatus:
			ShowMainMenu();
			break;
		default:
			break;
		}
		
	}

}

