package ru.overprof.pyatnashki;

import java.util.Random;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.background.ColorBackground;
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
import org.anddev.andengine.ui.activity.BaseGameActivity;
import org.anddev.andengine.util.HorizontalAlign;


import android.R.anim;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Toast;


public class PyatnashkiActivity extends BaseGameActivity {
	
	public static final int CAMERA_WIDTH = 600;
	public static final int CAMERA_HEIGHT = 500;	

	public static PyatnashkiActivity main_;
	
	public static Camera mCamera;
	
	public static MainState mainState_;
	
	boolean gameLoaded_ = false;
	 
	private Texture mYa;
	public static TextureRegion mYaTextureRegion;
	
	private Texture mAlexey;
	public static TextureRegion mAlexeyTextureRegion;
	
	private Texture mFontTexture;
	public static Font mFont;
	
	public static Boolean REALITY = true;
	
	@Override
	public Engine onLoadEngine() {
		Toast.makeText(this, "Пятнашки", Toast.LENGTH_LONG).show();
		main_ = this;
		this.mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		return new Engine(new EngineOptions(true, ScreenOrientation.LANDSCAPE, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), this.mCamera));
	}
	
	@Override
	public void onLoadResources() {
		this.mYa = new Texture(64, 64, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mYaTextureRegion = TextureRegionFactory.createFromAsset(this.mYa, this, "gfx/me64.png", 0, 0);
		
		this.mAlexey = new Texture(64,64, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mAlexeyTextureRegion = TextureRegionFactory.createFromAsset(this.mAlexey, this, "gfx/leha64.jpg",0,0);

		this.mEngine.getTextureManager().loadTexture(this.mYa);
		this.mEngine.getTextureManager().loadTexture(this.mAlexey);		
		
		this.mFontTexture = new Texture(256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mFont = new Font(this.mFontTexture, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 32, true, Color.WHITE);

		this.mEngine.getTextureManager().loadTexture(this.mFontTexture);
		this.mEngine.getFontManager().loadFont(this.mFont);
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


 

