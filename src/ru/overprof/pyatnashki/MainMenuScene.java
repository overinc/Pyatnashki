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
		
		Sprite startButton = new Sprite(0, 0, PyatnashkiActivity.mStartTextureRegion)
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
		
		//startButton.setScale((float)0.8);
		startButton.setPosition(PyatnashkiActivity.CAMERA_WIDTH - startButton.getWidth() - 50, PyatnashkiActivity.CAMERA_HEIGHT / 2 - startButton.getHeight() / 2);
		
		
		String s = PyatnashkiStrings.strPlay;
		Text gameText = new Text(10000, 10000, PyatnashkiActivity.menuFont, s);
		startButton.attachChild(gameText);	
		
		
		this.attachChild(startButton);
		registerTouchArea(startButton);		
		
		
		
		
		
		// ÐÅÊÎÐÄÛ

		Rectangle bottomPanel = new Rectangle(0, PyatnashkiActivity.CAMERA_HEIGHT - bottomPanelHeight_, PyatnashkiActivity.CAMERA_WIDTH, bottomPanelHeight_);
		bottomPanel.setColor(0, 0, 0, (float) 0.5);
		this.attachChild(bottomPanel);
		
		Sprite bestRamka = new Sprite(bottomPanel.getWidth() / 2 - 155 / 2, 0, PyatnashkiActivity.mBestRamkaTextureRegion);
		bottomPanel.attachChild(bestRamka);
		
		s = "BEST";
		//Text bestText = new Text(0, 0, PyatnashkiActivity.menuFont, s);
		//bestText.setPosition(bottomPanel.getWidth() / 2 - bestText.getWidth() / 2 , bottomPanel.getHeight() / 2 - bestText.getHeight() / 2);
		//bottomPanel.attachChild(bestText);
		
		
		String stepsRecordText = PyatnashkiActivity.resourses.getString(R.string.steps) + ": ";		
		Text stepsRecordControl = new Text(0, 0, PyatnashkiActivity.menuFont, stepsRecordText);
		stepsRecordControl.setPosition(bestRamka.getWidth() / 2 - stepsRecordControl.getWidth() / 2 - 10, (bottomPanel.getHeight() - stepsRecordControl.getHeight()) / 2);		
		bottomPanel.attachChild(stepsRecordControl);
		
		stepsRecordControlSave_ = new ChangeableText(0, 0, PyatnashkiActivity.menuFont, "0123456789??:", 15);
		stepsRecordControlSave_.setPosition(stepsRecordControl.getX() + stepsRecordControl.getWidth() + 10, (bottomPanel.getHeight() - stepsRecordControlSave_.getHeight()) / 2);		
		bottomPanel.attachChild(stepsRecordControlSave_);
		
		
		//String timeRecordText = PyatnashkiStrings.strTime + ": ";
		s = PyatnashkiActivity.resourses.getString(R.string.time) + ": ";
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
		
		checkBoxRealisticGame.setPosition(500, PyatnashkiActivity.CAMERA_HEIGHT / 2 - 72);
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
		
		checkBoxHelping.setPosition(500, PyatnashkiActivity.CAMERA_HEIGHT / 2 + 15);
		attachChild(checkBoxHelping);		
		registerTouchArea(checkBoxHelping);
		
		

		Text realisticText = new Text(50, 150, PyatnashkiActivity.mNastroikaFont, PyatnashkiActivity.resourses.getString(R.string.realistik));
		realisticText.setPosition(checkBoxRealisticGame.getX() - realisticText.getWidth() - 25, checkBoxRealisticGame.getY() - 3);
		attachChild(realisticText);
	    
	    Text helpText = new Text(50, 240, PyatnashkiActivity.mNastroikaFont, PyatnashkiActivity.resourses.getString(R.string.help));
	    helpText.setPosition(checkBoxHelping.getX() - helpText.getWidth() - 25, checkBoxHelping.getY() - 3);
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
