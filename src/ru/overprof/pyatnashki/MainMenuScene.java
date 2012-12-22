package ru.overprof.pyatnashki;

import java.util.Random;

import org.anddev.andengine.entity.primitive.Rectangle;
import org.anddev.andengine.entity.scene.CameraScene;
import org.anddev.andengine.entity.scene.background.ColorBackground;
import org.anddev.andengine.entity.scene.background.SpriteBackground;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.entity.text.ChangeableText;
import org.anddev.andengine.entity.text.Text;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.font.Font;
import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import android.R.integer;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.NetworkInfo.DetailedState;
import android.util.Log;

public class MainMenuScene extends CameraScene {
	
	public static int bottomPanelHeight_ = 80;
	
	
	
	final ChangeableText stepsRecordControlSave_;
	final ChangeableText stepsTimeControlSave_;
	
	public MainMenuScene(int pLayerCount) {		
		super(pLayerCount, PyatnashkiActivity.mCamera);
		
		this.setBackground(new SpriteBackground(PyatnashkiActivity.mMenuBackground));
		
		
		// ÊÍÎÏÊÀ ÑÒÀÐÒÀ
		
		Rectangle startButton = new Rectangle(50, 50, 200, 50)
		{
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (pSceneTouchEvent.isActionDown()) {
					MainState.ShowGameScene();
					return true;
				}
				return false;
			}
		};
		startButton.setPosition(PyatnashkiActivity.CAMERA_WIDTH - startButton.getWidth() - 50, PyatnashkiActivity.CAMERA_HEIGHT / 2 - startButton.getHeight() / 2);

		String s = PyatnashkiStrings.strPlay;
		Text gameText = new Text(0, 0, PyatnashkiActivity.menuFont, s);
		startButton.attachChild(gameText);		
		
		startButton.setColor(0, 0, 0, 0);
		attachChild(startButton);
		registerTouchArea(startButton);		
		
		
		
		
		
		// ÐÅÊÎÐÄÛ

		Rectangle bottomPanel = new Rectangle(0, PyatnashkiActivity.CAMERA_HEIGHT - bottomPanelHeight_, PyatnashkiActivity.CAMERA_WIDTH, bottomPanelHeight_);
		bottomPanel.setColor(0, 0, 0, (float) 0.7);
		attachChild(bottomPanel);
		
		s = "best";
		Text bestText = new Text(0, 0, PyatnashkiActivity.menuFont, s);
		bestText.setPosition(bottomPanel.getWidth() / 2 - bestText.getWidth() / 2 , bottomPanel.getHeight() / 2 - bestText.getHeight() / 2);
		bottomPanel.attachChild(bestText);
		
		
		String stepsRecordText = PyatnashkiStrings.strSteps + ": ";		
		Text stepsRecordControl = new Text(0, 0, PyatnashkiActivity.menuFont, stepsRecordText);
		stepsRecordControl.setPosition(0, (bottomPanel.getHeight() - stepsRecordControl.getHeight()) / 2);		
		bottomPanel.attachChild(stepsRecordControl);
		
		stepsRecordControlSave_ = new ChangeableText(0, 0, PyatnashkiActivity.menuFont, "0123456789??:", 15);
		stepsRecordControlSave_.setPosition(stepsRecordControl.getWidth() + 10, (bottomPanel.getHeight() - stepsRecordControlSave_.getHeight()) / 2);		
		bottomPanel.attachChild(stepsRecordControlSave_);
		
		
		//String timeRecordText = PyatnashkiStrings.strTime + ": ";
		s = PyatnashkiStrings.strTime + ": ";
		ChangeableText timeRecordControl = new ChangeableText(0, 0, PyatnashkiActivity.menuFont, "Âðåìÿ:::", 15);
		timeRecordControl.setText(s);
		timeRecordControl.setPosition(bottomPanel.getWidth() - timeRecordControl.getWidth() - 140, (bottomPanel.getHeight() - timeRecordControl.getHeight()) / 2);
		bottomPanel.attachChild(timeRecordControl);
		
		stepsTimeControlSave_ = new ChangeableText(0, 0, PyatnashkiActivity.menuFont, "0123456789??:", 15);
		stepsTimeControlSave_.setPosition(timeRecordControl.getX() + timeRecordControl.getWidth() + 10, (bottomPanel.getHeight() - stepsTimeControlSave_.getHeight()) / 2);		
		bottomPanel.attachChild(stepsTimeControlSave_);
		
		UpdateRecordsControls();
				
		
		
		// ÍÀÑÒÐÎÉÊÈ		
		
		AnimatedSprite checkBoxRealisticGame = new AnimatedSprite(450, 150, PyatnashkiActivity.mCheckBoxTextureRegion)
		{
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (pSceneTouchEvent.isActionDown()) {
					if (GameScene.REALITY) {
						this.setCurrentTileIndex(0);
						GameScene.REALITY = false;
					}
					else {
						this.setCurrentTileIndex(1);
						GameScene.REALITY = true;
					}
					SettingsScene.SaveSettings();
				}
				return true;								
			}
		};
		
		if (GameScene.REALITY)
			checkBoxRealisticGame.setCurrentTileIndex(1);
		else
			checkBoxRealisticGame.setCurrentTileIndex(0);		
		checkBoxRealisticGame.setScale((float)1.3);	
		
		attachChild(checkBoxRealisticGame);		
		registerTouchArea(checkBoxRealisticGame);
		
			
		TiledTextureRegion checkBoxTextureRegion = PyatnashkiActivity.mCheckBoxTextureRegion.clone();
		AnimatedSprite checkBoxHelping = new AnimatedSprite(450, 240, checkBoxTextureRegion)
		{
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (pSceneTouchEvent.isActionDown()) {
					if (GameScene.HELPING) {
						this.setCurrentTileIndex(0);
						GameScene.HELPING = false;
					}
					else {
						this.setCurrentTileIndex(1);
						GameScene.HELPING = true;
					}			
					SettingsScene.SaveSettings();
				}
				return true;								
			}
		};
		if (GameScene.HELPING)
			checkBoxHelping.setCurrentTileIndex(1);
		else
			checkBoxHelping.setCurrentTileIndex(0);
		checkBoxHelping.setScale((float)1.3);	
		
		attachChild(checkBoxHelping);		
		registerTouchArea(checkBoxHelping);
		
		

		Text realisticText = new Text(50, 150, PyatnashkiActivity.mFont, PyatnashkiStrings.strRealistikPyatn);
	    attachChild(realisticText);
	    
	    Text helpText = new Text(50, 240, PyatnashkiActivity.mFont, PyatnashkiStrings.strShowHelpPosition);
	    attachChild(helpText); 

	}
	
	public void Show() {
		
		setVisible(true);
		setIgnoreUpdate(false);
	}
	
	public void Hide() {
		
		setVisible(false);
		setIgnoreUpdate(true);
	}
	
	public static String convertSecondsToTime(int seconds) {
		if (seconds >= 60 * 60)
			return "Long(";
		int sec = seconds % 60;
		int min = seconds / 60;		
		String s=""; 
		if (min < 10)
			s += "0";
		s += Integer.toString(min);
		s += ":";
		if (sec < 10)
			s += "0";
		s += Integer.toString(sec);
		return s;
	}
	
	public void UpdateRecordsControls() {
		String stepsRecordString;
		if (RecordsScene.stepsRecordCount == 0)
			stepsRecordString = "??";
		else 
			stepsRecordString = String.valueOf(RecordsScene.stepsRecordCount);			
		stepsRecordControlSave_.setText(stepsRecordString);
		
		String timeRecordString;
		if (RecordsScene.timeRecordCount == 0)
			timeRecordString = "??";
		else
			timeRecordString = convertSecondsToTime(RecordsScene.timeRecordCount);
		stepsTimeControlSave_.setText(timeRecordString);
	}

}
