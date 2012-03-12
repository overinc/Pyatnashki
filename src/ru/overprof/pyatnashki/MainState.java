package ru.overprof.pyatnashki;

import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.input.touch.TouchEvent;

import android.view.KeyEvent;

public class MainState extends Scene {
	
	public static MainMenuScene mainMenuScene_ = new MainMenuScene(1);
	public static GameScene gameScene_ = new GameScene(1);
	
	 //private static int gameState_;
	static GAMESTATUS gameStatus_;
	
	public MainState(int pLayerCount) {
		super(pLayerCount);
		attachChild(mainMenuScene_);
		attachChild(gameScene_);
		
		ShowMainMenu();
		gameStatus_ = GAMESTATUS.MainMenuStatus; //?
		
	}
	
	public static void ShowGameScene() {
		gameScene_.Show();
		mainMenuScene_.Hide();
		gameStatus_ = GAMESTATUS.GamePlayingStatus;
	}
	
	public static void ShowMainMenu() {
		mainMenuScene_.Show();
		gameScene_.Hide();
		gameStatus_ = GAMESTATUS.MainMenuStatus;
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
		default:
			break;
		}
		//return super.onSceneTouchEvent(pSceneTouchEvent);
		return true;
	}
	
	
	enum GAMESTATUS{
		MainMenuStatus,
		SelectLevelsStatus,
		GamePlayingStatus;
	}


	public void KeyPressed(int keyCode, KeyEvent event) {
		switch (gameStatus_) 
		{		
		case MainMenuStatus:
			PyatnashkiActivity.main_.onDestroy();
			break;
		case GamePlayingStatus:	
			ShowMainMenu();
			break;
		default:
			break;
		}
		
	}

}

