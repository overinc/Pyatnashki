package ru.overprof.pyatnashki;

import java.lang.reflect.WildcardType;
import java.util.Random;
import java.util.Timer;

import org.anddev.andengine.engine.handler.timer.ITimerCallback;
import org.anddev.andengine.engine.handler.timer.TimerHandler;
import org.anddev.andengine.entity.primitive.Rectangle;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.background.ColorBackground;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.entity.text.ChangeableText;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import android.content.SharedPreferences.Editor;

import ru.overprof.pyatnashki.MainState.GAMESTATUS;


public class GameScene extends Scene {
	
	public static final int COUNTER = 4;
	public static final Validate LeftUpperAreaPoint = new Validate();
	public static final float Multiplexor = (float) 1.5;
	public static final int WidthPlitkaWithDistanse = (int) (PyatnashkiActivity.mYaTextureRegion.getWidth() / 2 * Multiplexor + 10);
	
	public static Boolean REALITY;
	public static Boolean HELPING;
	
	int touchedMovingPlitkaNumber = -1;
	
	int steps = 0;
	int seconds = 0;
	public boolean startActions_ = false;
	public boolean gamePaused_ = false;
	
	final ChangeableText counterOfSteps;
	public static final Plitka[] setOfTiles = new Plitka[COUNTER*COUNTER-1];
	
	public GameScene(int pLayerCount) {
		super(pLayerCount);		

		this.setBackground(new ColorBackground(0.01023f, 0.4867f, 0.2170f));		
		
		final ChangeableText time = new ChangeableText(PyatnashkiActivity.CAMERA_WIDTH-PyatnashkiActivity.mAlexeyTextureRegion.getWidth(), 90, PyatnashkiActivity.mFont, "0", 50);
		
		LeftUpperAreaPoint.x = PyatnashkiActivity.CAMERA_WIDTH / 2 - WidthPlitkaWithDistanse * 2 + 13/*?*/;
		LeftUpperAreaPoint.y = PyatnashkiActivity.CAMERA_HEIGHT / 2 - WidthPlitkaWithDistanse * 2 + 13/*?*/;	
		
		counterOfSteps = new ChangeableText(PyatnashkiActivity.CAMERA_WIDTH-PyatnashkiActivity.mAlexeyTextureRegion.getWidth(), 65, PyatnashkiActivity.mFont, "0", 50);
		this.getLastChild().attachChild(counterOfSteps);

		initAndOrSortSetOfTiles();
		
		this.registerUpdateHandler(new TimerHandler(1, true, 
				new ITimerCallback() {
			
			@Override
			public void onTimePassed(TimerHandler pTimerHandler) {
				
				if (MainState.gameStatus_ == GAMESTATUS.GamePlayingStatus && startActions_ && !gamePaused_) {
					time.setText(""+seconds);
					seconds++;
				}
				
			}
		}));
		
		this.getLastChild().attachChild(time);
		this.setTouchAreaBindingEnabled(true);
		
				
		Sprite restartButton = new Sprite(PyatnashkiActivity.CAMERA_WIDTH - PyatnashkiActivity.mRestartTextureRegion.getWidth() - 32, PyatnashkiActivity.CAMERA_HEIGHT / 2 - PyatnashkiActivity.mRestartTextureRegion.getHeight() / 2, PyatnashkiActivity.mRestartTextureRegion)
		{
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (pSceneTouchEvent.isActionDown()) {
					initAndOrSortSetOfTiles();
					counterOfSteps.setText("0");
					steps = 0;
					time.setText("0");
					seconds = 0;
					startActions_ = false;
					return true;
				} else
					return false;
				//MainState.ShowMainMenu();
				
			}
		};
		
		restartButton.setScale((float)1.5);
		attachChild(restartButton);
		registerTouchArea(restartButton);
	}
	
	public void Show() {
		
		setVisible(true);
		setIgnoreUpdate(false);
	}
	
	public void Hide() {
		
		setVisible(false);
		setIgnoreUpdate(true);
	}
	
	public void initAndOrSortSetOfTiles() {
		
		boolean sort = false;
		if (setOfTiles[0] != null) sort = true;
		
		int startRnd = 0;
		boolean[] rndMas = new boolean[15];		
		Validate startPos = null;
				
		for (int i = 0; i < COUNTER*COUNTER-1; i++) {			
			Random r = new Random();
			boolean b = false;
			
			while (!b){
				startRnd = r.nextInt(15);
				if (!rndMas[startRnd]){					
					rndMas[startRnd] = true;
					b = true;
				}
			}
			startPos = indexToPos(startRnd);
			
			if (sort) { // ��������� ������� ������
				setOfTiles[i].setPosition(LeftUpperAreaPoint.x + startPos.x * WidthPlitkaWithDistanse, LeftUpperAreaPoint.y + startPos.y * WidthPlitkaWithDistanse);
				setOfTiles[i].positionX = startPos.x;
				setOfTiles[i].positionY = startPos.y;
				setOfTiles[i].renewRightPos();
				setOfTiles[i].revalidateIt();
			} else // ������� ������ � ����		
			{ 	
				TiledTextureRegion plitkaTextureRegion = PyatnashkiActivity.mYaTextureRegion.clone();			

				setOfTiles[i] = new Plitka( startPos.x, startPos.y, plitkaTextureRegion, i + 1, PyatnashkiActivity.mFont){		
					@Override
					public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {						
						Validate val = IsEmptyNear(setOfTiles, this.positionX, this.positionY);							
						if (val.is) {							
							if (REALITY) { // ������� �����������
								float extremumRatio = 3 * WidthPlitkaWithDistanse / 5;
								if (val.x == this.positionX) { // ������������ ��������
									float startCoord = LeftUpperAreaPoint.y + this.positionY * WidthPlitkaWithDistanse;
									float extremumCoord;								
									float touchCoord = pSceneTouchEvent.getY() - this.getHeight() / 2;
									if (this.positionY < val.y) { // �������� ����
										extremumCoord = LeftUpperAreaPoint.y + this.positionY * WidthPlitkaWithDistanse + extremumRatio;
										if (pSceneTouchEvent.isActionDown()) {
											if (touchedMovingPlitkaNumber == -1)
												touchedMovingPlitkaNumber = id;
										}
										if (touchedMovingPlitkaNumber != id) {
											return false;
										}
										
										if (pSceneTouchEvent.isActionMove()) {											
											if (touchCoord >= startCoord && touchCoord <= startCoord + WidthPlitkaWithDistanse) {
												this.setPosition(this.getX(), touchCoord);								
											}
										}										
										if (pSceneTouchEvent.isActionUp()) {
											touchedMovingPlitkaNumber = -1;
											if (!startActions_)
												startActions_ = true;
											if (touchCoord < extremumCoord) 
												this.setPosition(this.getX(), startCoord);
											else {
												this.setPosition(this.getX(), LeftUpperAreaPoint.y + val.y * WidthPlitkaWithDistanse);
												this.positionY = val.y;
												this.renewRightPos();
												steps++;
												String s;
												s = String.format("%01d",steps);
												counterOfSteps.setText(s);
												CheckWin();
											}
										}										
									} else { // �������� �����
										extremumCoord = LeftUpperAreaPoint.y + this.positionY * WidthPlitkaWithDistanse - extremumRatio;
										if (pSceneTouchEvent.isActionDown()) {
											if (touchedMovingPlitkaNumber == -1)
												touchedMovingPlitkaNumber = id;
										}
										if (touchedMovingPlitkaNumber != id) {
											return false;
										}
										if (pSceneTouchEvent.isActionMove()) {
											if (touchCoord < startCoord && touchCoord > startCoord - WidthPlitkaWithDistanse)
												this.setPosition(this.getX(), touchCoord);
										}
										if (pSceneTouchEvent.isActionUp()) {
											touchedMovingPlitkaNumber = -1;
											if (!startActions_)
												startActions_ = true;
											if (/*touchCoord < startCoord && */touchCoord > extremumCoord)
												this.setPosition(this.getX(), startCoord);
											else/* if (touchCoord <= extremumCoord) */{
												this.setPosition(this.getX(), LeftUpperAreaPoint.y + val.y * WidthPlitkaWithDistanse);
												this.positionY = val.y;
												this.renewRightPos();
												steps++;
												String s;
												s = String.format("%01d",steps);
												counterOfSteps.setText(s);
												CheckWin();
											}
										}
									}
								}
								else { // �������������� ��������
									float startCoord = LeftUpperAreaPoint.x + this.positionX * WidthPlitkaWithDistanse;
									float extremumCoord;								
									float touchCoord = pSceneTouchEvent.getX() - this.getWidth() / 2;
									if (this.positionX < val.x) { // �������� ������
										extremumCoord = LeftUpperAreaPoint.x + this.positionX * WidthPlitkaWithDistanse + extremumRatio;
										if (pSceneTouchEvent.isActionDown()) {
											if (touchedMovingPlitkaNumber == -1)
												touchedMovingPlitkaNumber = id;
										}
										if (touchedMovingPlitkaNumber != id) {
											return false;
										}
										if (pSceneTouchEvent.isActionMove()) {
											if (touchCoord >= startCoord && touchCoord <= startCoord + WidthPlitkaWithDistanse)
												this.setPosition(touchCoord, this.getY());
										}
										if (pSceneTouchEvent.isActionUp()) {
											touchedMovingPlitkaNumber = -1;
											if (!startActions_)
												startActions_ = true;
											if (/*touchCoord > startCoord && */touchCoord < extremumCoord)
												this.setPosition(startCoord, this.getY());
											else /*if (touchCoord >= extremumCoord) */{
												this.setPosition(startCoord + WidthPlitkaWithDistanse, this.getY());
												this.positionX = val.x;
												this.renewRightPos();
												steps++;
												String s;
												s = String.format("%01d",steps);
												counterOfSteps.setText(s);
												CheckWin();
											}
										}
									}
									else { // �������� �����
										extremumCoord = LeftUpperAreaPoint.x + this.positionX * WidthPlitkaWithDistanse - extremumRatio;
										if (pSceneTouchEvent.isActionDown()) {
											if (touchedMovingPlitkaNumber == -1)
												touchedMovingPlitkaNumber = id;
										}
										if (touchedMovingPlitkaNumber != id) {
											return false;
										}
										if (pSceneTouchEvent.isActionMove()) {
											if (touchCoord < startCoord && touchCoord > startCoord - WidthPlitkaWithDistanse)
												this.setPosition(touchCoord, this.getY());
										}
										if (pSceneTouchEvent.isActionUp()) {
											touchedMovingPlitkaNumber = -1;
											if (!startActions_)
												startActions_ = true;
											if (/*touchCoord < startCoord && */touchCoord > extremumCoord)
												this.setPosition(startCoord, this.getY());
											else /*if (touchCoord <= extremumCoord)*/ {
												this.setPosition(startCoord - WidthPlitkaWithDistanse, this.getY());
												this.positionX = val.x;
												this.renewRightPos();
												steps++;
												String s;
												s = String.format("%01d",steps);
												counterOfSteps.setText(s);
												CheckWin();
											}
										}
									}
								}

							}
							else { // ���������� �����������
								if (pSceneTouchEvent.isActionDown()){													
									if (val.is){	
										if (!startActions_)
											startActions_ = true;
										this.setPosition(LeftUpperAreaPoint.x + val.x * WidthPlitkaWithDistanse, LeftUpperAreaPoint.y + val.y * WidthPlitkaWithDistanse);
										this.positionX = val.x;
										this.positionY = val.y;
										this.renewRightPos();
										steps++;
										String s;
										s = String.format("%01d",steps);
										counterOfSteps.setText(s);
										CheckWin();
									}
								}
							}	
							return true;
						}
						return false;
					}

				};
				setOfTiles[i].setScale(Multiplexor);
				this.getLastChild().attachChild(setOfTiles[i]);
				this.registerTouchArea(setOfTiles[i]);
			}
		}
		
		verifyTrueStartPos();		
	}
	
	private void verifyTrueStartPos() {
		int inversCount = 0;
		
		for (int i = 0; i < COUNTER * COUNTER - 1; i++)			
			for (int j = 0; j < i; j++) {
				int val1 = posToIndex(setOfTiles[j].positionX, setOfTiles[j].positionY);
				int val2 = posToIndex(setOfTiles[i].positionX, setOfTiles[i].positionY);
				if (val1 > val2)
					inversCount++;
			}
		inversCount += 4;			
		 
		if (inversCount % 2 == 0)
			return;
		else
			initAndOrSortSetOfTiles();
		
	}
	
	public Validate IsEmptyNear(Plitka[] set, int posx, int posy){
		boolean[][] temp = new boolean[COUNTER][COUNTER]; 
		int k=0;
		int l=0;
		for (int i = 0; i < COUNTER*COUNTER-1; i++){
			k = set[i].positionX;
			l = set[i].positionY;
			temp[k][l] = true;
		}
		for (int i = 0; i < COUNTER; i++)
			for (int j = 0; j < COUNTER; j++)
				if (!temp[i][j]){
					k = i;
					l = j;
					break;
				}
		Validate val = new Validate();				
		if (((posx-k >= -1 && posx - k <= 1) && (posy-l == 0)) || ((posx-k == 0) && (posy - l >= -1 && posy - l <= 1))){
			val.is = true;
			val.x = k;
			val.y = l;
			return val;
		}
		else{
			val.is = false;
			return val;
		}
	}
	
	private void CheckWin() {
		boolean win = true;
		for (int i = 0; i < COUNTER * COUNTER - 1; i++)
			if (!setOfTiles[i].isRightPos)
				win = false;	
		if (win) {
			MainState.gameStatus_ = GAMESTATUS.MainMenuStatus;
			startActions_ = false;
			counterOfSteps.setText("URAAAAAAAAAAAA");
			
			Editor editor = PyatnashkiActivity.mSettings.edit();
			if (steps < RecordsScene.stepsRecordCount || RecordsScene.stepsRecordCount == 0) {
				RecordsScene.stepsRecordCount = steps;
				editor.putInt(PyatnashkiActivity.APP_RECORDS_STEPS, steps);	
			}
			if (seconds < RecordsScene.timeRecordCount || RecordsScene.timeRecordCount == 0) {
				RecordsScene.timeRecordCount = seconds;
				editor.putInt(PyatnashkiActivity.APP_RECORDS_TIME, seconds);
			}
			editor.commit();
			MainState.ShowMainMenu();
		}
	}
	
	public Validate indexToPos(int ind)
	{
		Validate pos = new Validate();
		pos.x = ind % COUNTER;
		pos.y = (ind - (pos.x))/COUNTER;
		return pos;
	}	
	
	private	int posToIndex(int x, int y)
	{	
		return (y * COUNTER + x);
	}	

}
