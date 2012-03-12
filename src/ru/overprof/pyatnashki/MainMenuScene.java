package ru.overprof.pyatnashki;

import org.anddev.andengine.entity.primitive.Rectangle;
import org.anddev.andengine.entity.scene.CameraScene;
import org.anddev.andengine.entity.scene.background.ColorBackground;
import org.anddev.andengine.input.touch.TouchEvent;

import android.graphics.Color;

public class MainMenuScene extends CameraScene {
	
	public MainMenuScene(int pLayercuont) {		
		super(pLayercuont, PyatnashkiActivity.mCamera);
		
		//setBackgroundEnabled(false);
		this.setBackground(new ColorBackground(0.8698f, 0.7367f, 0.2830f));
		
		Rectangle menuItem1 = new Rectangle(50, 50, 200, 50)
		{
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				MainState.ShowGameScene();
				return false;
			}
		};
		
		Rectangle menuItem2 = new Rectangle(50, 150, 200, 50)
		{
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				MainState.ShowGameScene();
				return false;
			}
		};
		
		Rectangle menuItem3 = new Rectangle(50, 250, 200, 50)
		{
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				MainState.ShowGameScene();
				return false;
			}
		};
		
		menuItem1.setColor(54, 45, 36);
		attachChild(menuItem1);
		registerTouchArea(menuItem1);
		
		menuItem2.setColor(54, 45, 36);
		attachChild(menuItem2);
		registerTouchArea(menuItem2);
		
		menuItem3.setColor(54, 45, 36);
		attachChild(menuItem3);
		registerTouchArea(menuItem3);
	}
	
	public void Show() {
		
		setVisible(true);
		setIgnoreUpdate(false);
	}
	
	public void Hide() {
		
		setVisible(false);
		setIgnoreUpdate(true);
	}

}
