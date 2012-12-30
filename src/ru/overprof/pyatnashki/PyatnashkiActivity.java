package ru.overprof.pyatnashki;

import java.util.Random;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.background.ColorBackground;
import org.anddev.andengine.entity.sprite.BaseSprite;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.entity.text.ChangeableText;
import org.anddev.andengine.entity.text.Text;
import org.anddev.andengine.entity.util.FPSLogger;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.font.Font;
import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.opengl.texture.region.TextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;
import org.anddev.andengine.ui.activity.BaseGameActivity;
import org.anddev.andengine.util.HorizontalAlign;


import android.R.anim;
import android.R.string;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Toast;


public class PyatnashkiActivity extends BaseGameActivity {
	
	public static final int CAMERA_WIDTH = 800;
	public static final int CAMERA_HEIGHT = 480;	

	public static PyatnashkiActivity main_;
	
	public static Camera mCamera;
	
	public static MainState mainState_;
	
	boolean gameLoaded_ = false;
	 
	private Texture mYa;
	public static TiledTextureRegion mYaTextureRegion;
	
	private Texture mWinTexture;
	public static TextureRegion mWinTextureRegion;
	
	private Texture mRestartTexture;
	public static TextureRegion mRestartTextureRegion;
	
	private Texture mStartTexture;
	public static TextureRegion mStartTextureRegion;
	
	private Texture mGameBottomPanelTexture;
	public static TextureRegion mGameBottomPanelTextureRegion;
	
	private Texture mBestRamkaTexture;
	public static TextureRegion mBestRamkaTextureRegion;
	
	private Texture mFontTexture;
	public static Font mFont;
	
	private Texture menuFontTexture;
	public static Font menuFont;
	
	private Texture mNastroikaTexture;
	public static Font mNastroikaFont;
	
	private Texture mGameBackgroundTexture;
	private TextureRegion mGameBackgroundTextureRegion;
	public static BaseSprite mGameBackground;
	
	private Texture mMenuBackgroundTexture;
	private TextureRegion mMenuBackgroundTextureRegion;
	public static BaseSprite mMenuBackground;
	
	private Texture mCheckBoxTexture;
	public static TiledTextureRegion mCheckBoxTextureRegion;
	
	String language_ = "ru";
	public static Resources resourses;
	
	public static final String APP_PREFERENSES = "settings";
	public static final String PREF_TYPE_GAME = "type";
	public static final String PREF_HELP_GAME = "help";
	public static final String APP_RECORDS_STEPS = "recordsteps";
	public static final String APP_RECORDS_TIME = "recordseconds";
	
	public static SharedPreferences mSettings;
	
	@Override
	public Engine onLoadEngine() {
		//Toast.makeText(this, "Пятнашки", Toast.LENGTH_LONG).show();
		main_ = this;
		this.mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		return new Engine(new EngineOptions(true, ScreenOrientation.LANDSCAPE, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), this.mCamera));
	}
	
	@Override
	public void onLoadResources() {
		
		resourses = getResources();
		
		mYa = new Texture(128, 64, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		mYaTextureRegion = TextureRegionFactory.createTiledFromAsset(this.mYa, this, "gfx/plitka.png", 0, 0, 2, 1);
		
		mWinTexture = new Texture(256, 128, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		mWinTextureRegion = TextureRegionFactory.createFromAsset(mWinTexture, this, "gfx/win_window.png", 0, 0);
		
		mRestartTexture = new Texture(128, 128, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		mRestartTextureRegion = TextureRegionFactory.createFromAsset(this.mRestartTexture, this, "gfx/restart_button.png", 0 , 0);
		
		mStartTexture = new Texture(128, 128, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		mStartTextureRegion = TextureRegionFactory.createFromAsset(this.mStartTexture, this, "gfx/start_button.png", 0 , 0);
		
		mGameBackgroundTexture = new Texture(1024, 512, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		mGameBackgroundTextureRegion = TextureRegionFactory.createFromAsset(this.mGameBackgroundTexture, this, "gfx/Game_background.jpg", 0 , 0);
		mGameBackground = new BaseSprite(0, 0, 800, 480, mGameBackgroundTextureRegion) {};
		
		mMenuBackgroundTexture = new Texture(1024, 512, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		mMenuBackgroundTextureRegion = TextureRegionFactory.createFromAsset(this.mMenuBackgroundTexture, this, "gfx/Main_menu_background.jpg", 0 , 0);
		mMenuBackground = new BaseSprite(0, 0, 800 ,480, mMenuBackgroundTextureRegion) {};
		
		mGameBottomPanelTexture = new Texture(512, 64, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		mGameBottomPanelTextureRegion = TextureRegionFactory.createFromAsset(this.mGameBottomPanelTexture, this, "gfx/bottom_panel_center.png", 0 , 0);
		
		mBestRamkaTexture = new Texture(256, 128, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		mBestRamkaTextureRegion = TextureRegionFactory.createFromAsset(this.mBestRamkaTexture, this, "gfx/best_ramka.png", 0 , 0);
				
		mCheckBoxTexture = new Texture(64, 32, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		mCheckBoxTextureRegion = TextureRegionFactory.createTiledFromAsset(mCheckBoxTexture, this, "gfx/check_box.png", 0, 0, 2, 1);
				
		this.mEngine.getTextureManager().loadTexture(mYa);
		this.mEngine.getTextureManager().loadTexture(mWinTexture);
		this.mEngine.getTextureManager().loadTexture(mRestartTexture);
		this.mEngine.getTextureManager().loadTexture(mStartTexture);
		this.mEngine.getTextureManager().loadTexture(mGameBackgroundTexture);
		this.mEngine.getTextureManager().loadTexture(mMenuBackgroundTexture);	
		this.mEngine.getTextureManager().loadTexture(mCheckBoxTexture);		
		this.mEngine.getTextureManager().loadTexture(mGameBottomPanelTexture);
		this.mEngine.getTextureManager().loadTexture(mBestRamkaTexture);
		
		mFontTexture = new Texture(256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		mFont = new Font(this.mFontTexture, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 32, true, Color.WHITE);
		
		menuFontTexture = new Texture(256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		menuFont = new Font(menuFontTexture, Typeface.create(Typeface.DEFAULT, Typeface.ITALIC), 40, true, Color.YELLOW);
		
		mNastroikaTexture = new Texture(256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		mNastroikaFont = new Font(this.mNastroikaTexture, Typeface.create(Typeface.DEFAULT, Typeface.NORMAL), 33, true, Color.WHITE/*Color.rgb(0, 210, 213)*/);
		
		this.mEngine.getTextureManager().loadTexture(this.mFontTexture);
		this.mEngine.getFontManager().loadFont(this.mFont);
		this.mEngine.getTextureManager().loadTexture(this.menuFontTexture);
		this.mEngine.getFontManager().loadFont(this.menuFont);
		this.mEngine.getTextureManager().loadTexture(this.mNastroikaTexture);
		this.mEngine.getFontManager().loadFont(this.mNastroikaFont);
		
		PyatnashkiStrings strings = new PyatnashkiStrings(language_);
		
		mSettings = getSharedPreferences(APP_PREFERENSES, Context.MODE_PRIVATE);
		if (mSettings.contains(PREF_TYPE_GAME))
			GameScene.REALITY = mSettings.getBoolean(PREF_TYPE_GAME, true);
		else
			GameScene.REALITY = true;
		if (mSettings.contains(PREF_HELP_GAME))
			GameScene.HELPING = mSettings.getBoolean(PREF_HELP_GAME, true);
		else
			GameScene.HELPING = true;
		if (mSettings.contains(APP_RECORDS_STEPS))
			RecordsScene.stepsRecordCount = mSettings.getInt(APP_RECORDS_STEPS, 0);
		else
			RecordsScene.stepsRecordCount = 0;
		if (mSettings.contains(APP_RECORDS_TIME))
			RecordsScene.timeRecordCount = mSettings.getInt(APP_RECORDS_TIME, 0);
		else
			RecordsScene.timeRecordCount = 0;
	}
	
	@Override
	public Scene onLoadScene() {		
		
		this.mEngine.registerUpdateHandler(new FPSLogger());

		//final Scene scene = new Scene(1);
		mainState_ = new MainState(1);	
		gameLoaded_ = true;
		
		return mainState_;
	}
	
	@Override
	public void onLoadComplete() {
		
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK){
			if (!gameLoaded_) return true;
			if (mainState_ != null && gameLoaded_){
				mainState_.KeyPressed(keyCode, event);
				return true;
			}
			return true;
		}		
		return super.onKeyDown(keyCode, event);
	}
	
	@Override
	protected void onDestroy() {		
		super.onDestroy();
		android.os.Process.killProcess(android.os.Process.myPid());
	}
	
}

final class Validate{
	public Validate() {
		// TODO Auto-generated constructor stub
	}
	public Validate(boolean b,int i1,int i2) {
		is = b;
		x = i1;
		y = i2;
	}
	boolean is;
	int x;
	int y;
}


 

