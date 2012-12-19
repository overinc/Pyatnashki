package ru.overprof.pyatnashki;

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

import android.graphics.Color;
import android.graphics.Typeface;

public class MainMenuScene extends CameraScene {
	
	private int bottomPanelHeight_ = 80;
	
	public MainMenuScene(int pLayerCount) {		
		super(pLayerCount, PyatnashkiActivity.mCamera);
		
		this.setBackground(new SpriteBackground(PyatnashkiActivity.mMenuBackground));
		
		
		// ������ ������
		
		Rectangle startButton = new Rectangle(50, 50, 200, 50)
		{
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				MainState.ShowGameScene();
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
		
		
		
		
		
		// �������

		Rectangle bottomPanel = new Rectangle(0, PyatnashkiActivity.CAMERA_HEIGHT - bottomPanelHeight_, PyatnashkiActivity.CAMERA_WIDTH, bottomPanelHeight_);
		bottomPanel.setColor(0, 0, 0, 153);
		attachChild(bottomPanel);
		
		s = "best";// of the best";
		Text bestText = new Text(0, 0, PyatnashkiActivity.menuFont, s);
		bestText.setPosition(bottomPanel.getWidth() / 2 - bestText.getWidth() / 2 , bottomPanel.getHeight() / 2 - bestText.getHeight() / 2);
		//attachChild(bestText);
		bottomPanel.attachChild(bestText);
		
		String stepsRecordString;
		if (RecordsScene.stepsRecordCount == 0)
			stepsRecordString = PyatnashkiStrings.strSteps + ": ??";
		else 
			stepsRecordString = PyatnashkiStrings.strSteps + ": " + RecordsScene.stepsRecordCount;
		Text stepsRecordControl = new Text(0, 0, PyatnashkiActivity.menuFont, stepsRecordString);
		stepsRecordControl.setPosition(0, (bottomPanel.getHeight() - stepsRecordControl.getHeight()) / 2);
		bottomPanel.attachChild(stepsRecordControl);
		
		String timeRecordString;
		if (RecordsScene.timeRecordCount == 0)
			timeRecordString = PyatnashkiStrings.strTime + ": ??";
		else
			timeRecordString = PyatnashkiStrings.strTime + ": " + RecordsScene.timeRecordCount;		
		Text timeRecordControl = new Text(0, 0, PyatnashkiActivity.menuFont, timeRecordString);
		timeRecordControl.setPosition(bottomPanel.getWidth() - timeRecordControl.getWidth() - 10, (bottomPanel.getHeight() - timeRecordControl.getHeight()) / 2);
		bottomPanel.attachChild(timeRecordControl);
		
		
		
		
		// ���������		
		
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

}
