package ru.overprof.pyatnashki;

import org.anddev.andengine.entity.primitive.Rectangle;
import org.anddev.andengine.entity.scene.CameraScene;
import org.anddev.andengine.entity.scene.background.ColorBackground;
import org.anddev.andengine.entity.text.Text;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.font.Font;
import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.TextureOptions;

import android.graphics.Color;
import android.graphics.Typeface;

public class MainMenuScene extends CameraScene {
	
	public MainMenuScene(int pLayerCount) {		
		super(pLayerCount, PyatnashkiActivity.mCamera);
		
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

		String s = "Играем";
		Text gameText = new Text(0, 0, PyatnashkiActivity.menuFont, s);
		menuItem1.attachChild(gameText);
		
		Rectangle menuItem2 = new Rectangle(50, 150, 200, 50)
		{
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				MainState.ShowSettings();
				return false;
			}
		};
		
		s = "Настроить";
		Text settingsText = new Text(0, 0, PyatnashkiActivity.menuFont, s);
		menuItem2.attachChild(settingsText);
		
		Rectangle menuItem3 = new Rectangle(50, 250, 200, 50)
		{
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				
				return false;
			}
		};
		
		s = "Рекорды";
		Text recorgsText = new Text(0, 0, PyatnashkiActivity.menuFont, s);
		menuItem3.attachChild(recorgsText);
		
		
		
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
