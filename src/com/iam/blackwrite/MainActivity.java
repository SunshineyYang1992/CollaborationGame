package com.iam.blackwrite;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import model.CustomDialog;
import model.ExitApplication;
import model.preparezimu;
import org.anddev.andengine.audio.sound.Sound;
import org.anddev.andengine.audio.sound.SoundFactory;
import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.handler.timer.ITimerCallback;
import org.anddev.andengine.engine.handler.timer.TimerHandler;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.anddev.andengine.entity.IEntity;
import org.anddev.andengine.entity.modifier.AlphaModifier;
import org.anddev.andengine.entity.modifier.IEntityModifier;
import org.anddev.andengine.entity.modifier.LoopEntityModifier;
import org.anddev.andengine.entity.modifier.MoveModifier;
import org.anddev.andengine.entity.modifier.MoveXModifier;
import org.anddev.andengine.entity.modifier.ParallelEntityModifier;
import org.anddev.andengine.entity.modifier.ScaleModifier;
import org.anddev.andengine.entity.modifier.SequenceEntityModifier;
import org.anddev.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.anddev.andengine.entity.primitive.Line;
import org.anddev.andengine.entity.primitive.Rectangle;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.entity.text.ChangeableText;
import org.anddev.andengine.entity.text.Text;
import org.anddev.andengine.entity.util.FPSLogger;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.font.Font;
import org.anddev.andengine.opengl.font.FontFactory;
import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.opengl.texture.region.TextureRegionFactory;
import org.anddev.andengine.ui.activity.BaseGameActivity;
import org.anddev.andengine.util.HorizontalAlign;
import org.anddev.andengine.util.modifier.IModifier;
import org.anddev.andengine.util.modifier.LoopModifier;

import android.R.integer;
import android.os.Handler;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.hardware.SensorManager;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;
public class MainActivity extends BaseGameActivity {
	private static final int CAMERA_WIDTH =800;
	private static final int CAMERA_HEIGHT = 480;
	private Camera mCamera;
	private Handler mHandler;
	private SharedPreferences audioOptions;
	private SharedPreferences.Editor audioEditor;
	private Timer timer;
	private TimerTask task;
	private Boolean isbegin=false;
	private static final int MSG_COUNTER_DOWN=0;
	private int UPDATE_INTERVAL=20;
	protected Scene mMenuScene;
	protected Scene endScene;
	protected Scene mainScene;
	private Rectangle backimg1;
	private Texture wordTexture;
	private TextureRegion wordTextureRegion[]=new TextureRegion[26];
	
	private Texture gobackTexture;
	private TextureRegion gobackTextureRegion;
	private Texture playsoundTexture;
	private TextureRegion playsoundTextureRegion;
	private Texture refreshTexture;
	private TextureRegion refreshTextureRegion;
	
	Random random=new Random();
	Sprite leftobb[]=new Sprite[6];
	Sprite rightobb[]=new Sprite[6];
	Rectangle leftr[]=new Rectangle[6];
	Rectangle rightr[]=new Rectangle[6];
	
	Rectangle getin;
	Rectangle setup;
	//几个按钮的sprite
	Sprite goback;
	Sprite playmusic;
	Sprite refresh;
	//背景
	Rectangle back1;
	Line midline;
	private ChangeableText kidtext;
	private ChangeableText oldtext;
	//字体大小
	ChangeableText onesize;
	ChangeableText othersize;
	//动画快慢
	ChangeableText oneanimation;
	ChangeableText otheranimation;
	//当局的单词
	String precolor;
	//小孩打乱后数据
	int randomarray[];
	//大人打乱后数据
	int randomarray2[];
	//纪录一组每一个是否已经填充
	Boolean []isfill=new Boolean[6] ;
	protected Scene mStaticMenuScene;
	//设置场景
	protected Scene msetupScene;
	private Font mmFont;
	protected Texture mmFontTexture;
	private Font mscoreFont;
	protected Texture mscoreFontTexture;
	private Scene splashScene;
	protected Scene mOptionsMenuScene;
	//表示哪个走。true为老人走
	private boolean who=true;
	//纪录本组该走第几个
	private int step=0;
	private int kidHeiW=20;//小孩第一个目标块距离顶端距离
	private int oldHeiW=20;//老人第一个目标块距离顶端距离
	private int movebottoml=50;//移动块距离低端距离
	private int movetopl=30;//移动块距离顶端距离
	private int kidfontsize=80;
	private int oldfontsize=80;
	//老人和小孩动画速度0表示无动画
	private float kidan=0;
	private float oldan=0;
	ChangeableText myscoretextkid;
	ChangeableText myscoretextold;
	private int myscore=-1;
	
	Sound []lettersound=new Sound[26];
	Sound []colorsound=new Sound[preparezimu.color.length];
	Sound passsound;
	Sound errorsound;
	//显示设置界面音效开关
	ChangeableText soundtext;
	//物理世界
	//PhysicsWorld mPhysicsWorld;
	@Override
	public Engine onLoadEngine() {
		mHandler=new Handler();
		ExitApplication.getInstance().addActivity(this);
		this.mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		audioOptions=getSharedPreferences("audio", MODE_PRIVATE);
		audioEditor=audioOptions.edit();
		//音效是否打开
		if(!audioOptions.contains("effectsOn"))
		{
		audioEditor.putBoolean("effectsOn", true);
		audioEditor.commit();
		}
		//小孩字体大小0小1中2大
		if(!audioOptions.contains("kidfontsize"))
		{
		audioEditor.putInt("kidfontsize", 1);
		audioEditor.commit();
		}
		//老人字体大小1小2中3大
		if(!audioOptions.contains("oldfontsize"))
		{
		audioEditor.putInt("oldfontsize", 1);
		audioEditor.commit();
		}
		//小孩动画快慢1无2慢3快
		if(!audioOptions.contains("kidanimation"))
		{
		audioEditor.putInt("kidanimation", 1);
		audioEditor.commit();
		}
		//老人动画快慢1无2慢3快
		if(!audioOptions.contains("oldanimation"))
		{
		audioEditor.putInt("oldanimation", 1);
		audioEditor.commit();
		}
		// TODO Auto-generated method stub
		return new Engine(new EngineOptions(true, ScreenOrientation.LANDSCAPE,
				new FillResolutionPolicy(),
				this.mCamera).setNeedsSound(true).setNeedsMusic(true));
	}

	@Override
	public void onLoadResources() {
		// TODO Auto-generated method stub
		this.mmFontTexture=new Texture(512, 512, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mmFont=FontFactory.createFromAsset(mmFontTexture, this,"fonts/pod.ttf", 40, true, Color.rgb(254, 67, 101));
		
		this.mEngine.getTextureManager().loadTexture(mmFontTexture);
		this.mEngine.getFontManager().loadFont(mmFont);
		this.mmFont.prepareLettes("loading".toCharArray());
		
		this.mscoreFontTexture=new Texture(512, 512, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mscoreFont=FontFactory.createFromAsset(mscoreFontTexture, this,"fonts/pod.ttf", 25, true, Color.rgb(1, 0, 0));
		this.mEngine.getTextureManager().loadTexture(mscoreFontTexture);
		this.mEngine.getFontManager().loadFont(mscoreFont);
		this.mmFont.prepareLettes("0123456789".toCharArray());
		
		
	}
	@SuppressLint("NewApi")
	@Override
	public Scene onLoadScene() {
		// TODO Auto-generated method stub
		this.mMenuScene = new Scene(1);
		initSplashScene();
		mEngine.registerUpdateHandler(new TimerHandler(3f, new ITimerCallback() {
		@Override
		public void onTimePassed(TimerHandler pTimerHandler) {
			// TODO Auto-generated method stub
			loadResources();
			loadScenes();
			MainActivity.this.runOnUpdateThread(new Runnable() {
			    @Override
			    public void run() {
			    	splashScene.detachSelf();
			    }
			});
			mMenuScene.setChildScene(mainScene);
			mEngine.setScene(mMenuScene);
			}	
			}));
		return mMenuScene;
	}
	@SuppressWarnings("unchecked")
	protected void loadScenes() {
		// TODO Auto-generated method stub
		this.mEngine.registerUpdateHandler(new FPSLogger());
		mainScene=new Scene(2);
		back1=new Rectangle(0,0,CAMERA_WIDTH,CAMERA_HEIGHT);
		back1.setColor(1, 1, 1);
		mainScene.getLastChild().attachChild(back1);
		Text cooperationtext=new Text(0, 0, mmFont, "cooperation");
		Text settingtext=new Text(0, 0, mmFont, "setting");
		getin=new Rectangle(0, 0, 250, 150){
			@SuppressLint("NewApi")
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				// TODO Auto-generated method stub
				switch (pSceneTouchEvent.getAction()) {
				case TouchEvent.ACTION_DOWN:
					this.registerEntityModifier(new SequenceEntityModifier(
							new IEntityModifierListener() {
								@Override
								public void onModifierFinished(
										IModifier<IEntity> pModifier,
										IEntity pItem) {	
									creatGameScene();
									mMenuScene.clearChildScene();
									mMenuScene.setChildScene(mStaticMenuScene);
									mEngine.setScene(mMenuScene);
								}
							}, new ParallelEntityModifier(new AlphaModifier(
									0.1f, 1.0f, 0.0f), new AlphaModifier(0.1f,
									0.0f, 1.0f))));
					break;
				case TouchEvent.ACTION_UP:
					break;
				}
				return true;
			}
		};
		setup=new Rectangle(0, 0, 250, 150){
			@SuppressLint("NewApi")
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				// TODO Auto-generated method stub
				switch (pSceneTouchEvent.getAction()) {
				case TouchEvent.ACTION_DOWN:
					creatsetupScene();
					mMenuScene.clearChildScene();
					mMenuScene.setChildScene(msetupScene);
					mEngine.setScene(mMenuScene);
				break;
				case TouchEvent.ACTION_UP:
					break;
				}
				return true;
			}
		};
		getin.setColor(0, 0, 1);
		setup.setColor(0, 1, 0);
		getin.setPosition(100, (CAMERA_HEIGHT-getin.getHeight())/2);
		setup.setPosition(400, (CAMERA_HEIGHT-getin.getHeight())/2);
		cooperationtext.setPosition(120, (CAMERA_HEIGHT-cooperationtext.getHeight())/2);
		settingtext.setPosition(460, (CAMERA_HEIGHT-settingtext.getHeight())/2);
		mainScene.registerTouchArea(getin);
		mainScene.registerTouchArea(setup);
		mainScene.getLastChild().attachChild(setup);
		mainScene.getLastChild().attachChild(getin);
		mainScene.getLastChild().attachChild(cooperationtext);
		mainScene.getLastChild().attachChild(settingtext);
		//this.createStaticMenuScene();
		
	}
	public void creatsetupScene(){
		msetupScene=new Scene(2);
		Rectangle setupback=new Rectangle(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		setupback.setColor(1f, 1f, 1f);
		msetupScene.attachChild(setupback);
		onesize=new ChangeableText(0,0,mmFont,"kid size:small",18);
		othersize=new ChangeableText(0,0,mmFont,"old size:small",18);
		oneanimation=new ChangeableText(0,0,mmFont,"kid animation:null",20);
		otheranimation=new ChangeableText(0,0,mmFont,"old animation:null",20);
		
		if (audioOptions.getInt("kidfontsize", 1) == 1) {
			onesize.setText("kid size:small");
		}
		else if(audioOptions.getInt("kidfontsize", 0) == 2)
		{
			onesize.setText("kid size:medium");
		}
		else
		{
			onesize.setText("kid size:large");
		}
		if (audioOptions.getInt("oldfontsize", 1) == 1) {
			othersize.setText("old size:small");
		}
		else if(audioOptions.getInt("oldfontsize", 0) == 2)
		{
			othersize.setText("old size:medium");
		}
		else
		{
			othersize.setText("old size:large");
		}
		if (audioOptions.getInt("kidanimation", 1) == 1) {
			oneanimation.setText("kid animation:null");
		}
		else if(audioOptions.getInt("kidanimation", 0) == 2)
		{
			oneanimation.setText("kid animation:slow");
		}
		else
		{
			oneanimation.setText("kid animation:fast");
		}
		if (audioOptions.getInt("oldanimation", 1) == 1) {
			otheranimation.setText("old animation:null");
		}
		else if(audioOptions.getInt("oldanimation", 0) == 2)
		{
			otheranimation.setText("old animation:slow");
		}
		else
		{
			otheranimation.setText("old animation:fast");
		}
		Rectangle oner=new Rectangle(0, 0, 300, 80){
			@SuppressLint("NewApi")
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				// TODO Auto-generated method stub
				switch (pSceneTouchEvent.getAction()) {
				case TouchEvent.ACTION_DOWN:
					this.registerEntityModifier(new SequenceEntityModifier(
							new IEntityModifierListener() {
								@Override
								public void onModifierFinished(
										IModifier<IEntity> pModifier,
										IEntity pItem) {
									// TODO Auto-generated method stub
									if (audioOptions.getInt("kidfontsize", 1)==1) {
										audioEditor.putInt("kidfontsize", 2);
										onesize.setText("kid size:medium");
									} else if(audioOptions.getInt("kidfontsize", 1)==2){
										audioEditor.putInt("kidfontsize", 3);
										//text3.setText("音效:开");
										onesize.setText("kid size:large");
									}
									else
									{
										audioEditor.putInt("kidfontsize", 1);
										onesize.setText("kid size:small");
									}
									audioEditor.commit();
								}
							}, new ParallelEntityModifier(new AlphaModifier(
									0.1f, 1.0f, 0.0f), new AlphaModifier(0.1f,
									0.0f, 1.0f))));
					break;
				case TouchEvent.ACTION_UP:
					break;
				}
				return true;
			}
		};
		Rectangle otherr=new Rectangle(0, 0, 300, 80){
			@SuppressLint("NewApi")
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				// TODO Auto-generated method stub
				switch (pSceneTouchEvent.getAction()) {
				case TouchEvent.ACTION_DOWN:
					this.registerEntityModifier(new SequenceEntityModifier(
							new IEntityModifierListener() {
								@Override
								public void onModifierFinished(
										IModifier<IEntity> pModifier,
										IEntity pItem) {
									// TODO Auto-generated method stub
									if (audioOptions.getInt("oldfontsize", 1)==1) {
										audioEditor.putInt("oldfontsize", 2);
										othersize.setText("old size:medium");
									} else if(audioOptions.getInt("oldfontsize", 1)==2){
										audioEditor.putInt("oldfontsize", 3);
										//text3.setText("音效:开");
										othersize.setText("old size:large");
									}
									else
									{
										audioEditor.putInt("oldfontsize", 1);
										othersize.setText("old size:small");
									}
									audioEditor.commit();
								}
							}, new ParallelEntityModifier(new AlphaModifier(
									0.1f, 1.0f, 0.0f), new AlphaModifier(0.1f,
									0.0f, 1.0f))));
					break;
				case TouchEvent.ACTION_UP:
					break;
				}
				return true;
			}
		};
		Rectangle onear=new Rectangle(0, 0, 300, 80){
			@SuppressLint("NewApi")
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				// TODO Auto-generated method stub
				switch (pSceneTouchEvent.getAction()) {
				case TouchEvent.ACTION_DOWN:
					this.registerEntityModifier(new SequenceEntityModifier(
							new IEntityModifierListener() {
								@Override
								public void onModifierFinished(
										IModifier<IEntity> pModifier,
										IEntity pItem) {
									// TODO Auto-generated method stub
									if (audioOptions.getInt("kidanimation", 1)==1) {
										audioEditor.putInt("kidanimation", 2);
										oneanimation.setText("kid animation:slow");
									} else if(audioOptions.getInt("kidanimation", 1)==2){
										audioEditor.putInt("kidanimation", 3);
										//text3.setText("音效:开");
										oneanimation.setText("kid animation:fast");
									}
									else
									{
										audioEditor.putInt("kidanimation", 1);
										oneanimation.setText("old animation:null");
									}
									audioEditor.commit();
								}
							}, new ParallelEntityModifier(new AlphaModifier(
									0.1f, 1.0f, 0.0f), new AlphaModifier(0.1f,
									0.0f, 1.0f))));
					break;
				case TouchEvent.ACTION_UP:
					break;
				}
				return true;
			}
		};
		Rectangle otherar=new Rectangle(0, 0, 300, 80){
			@SuppressLint("NewApi")
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				// TODO Auto-generated method stub
				switch (pSceneTouchEvent.getAction()) {
				case TouchEvent.ACTION_DOWN:
					this.registerEntityModifier(new SequenceEntityModifier(
							new IEntityModifierListener() {
								@Override
								public void onModifierFinished(
										IModifier<IEntity> pModifier,
										IEntity pItem) {
									// TODO Auto-generated method stub
									if (audioOptions.getInt("oldanimation", 1)==1) {
										audioEditor.putInt("oldanimation", 2);
										otheranimation.setText("old animation:slow");
									} else if(audioOptions.getInt("oldanimation", 1)==2){
										audioEditor.putInt("oldanimation", 3);
										//text3.setText("音效:开");
										otheranimation.setText("old animation:fast");
									}
									else
									{
										audioEditor.putInt("oldanimation", 1);
										otheranimation.setText("old animation:null");
									}
									audioEditor.commit();
								}
							}, new ParallelEntityModifier(new AlphaModifier(
									0.1f, 1.0f, 0.0f), new AlphaModifier(0.1f,
									0.0f, 1.0f))));
					break;
				case TouchEvent.ACTION_UP:
					break;
				}
				return true;
			}
		};
		soundtext=new ChangeableText(0, 0, mmFont, "sounds:on",10);
		Rectangle soundsetting=new Rectangle(0,0,300,80)
		{
		
				@SuppressLint("NewApi")
				public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
						float pTouchAreaLocalX, float pTouchAreaLocalY) {
					// TODO Auto-generated method stub
					switch(pSceneTouchEvent.getAction()){
					case TouchEvent.ACTION_DOWN:
						this.registerEntityModifier(new SequenceEntityModifier(
								new ScaleModifier(0.1f,1f,1.3f),
								new ParallelEntityModifier(new AlphaModifier(0.1f,1.0f,0.0f),new ScaleModifier(0.1f,1.2f,1f),new AlphaModifier(0.1f,0.0f,1.0f))
								));
						if(audioOptions.getBoolean("effectsOn", true))
						{
							audioEditor.putBoolean("effectsOn", false);
							soundtext.setText("sounds:off");
						}
						else
						{
							audioEditor.putBoolean("effectsOn", true);
							soundtext.setText("sounds:on");
						}
						audioEditor.commit();
						break;
					case TouchEvent.ACTION_UP:
					break;		
					}
					return true;
					}
				};
		if(audioOptions.getBoolean("effectsOn", false))
		{
			soundtext.setText("sounds:on");
		}
		soundtext.setPosition((CAMERA_WIDTH-soundtext.getWidth())/2,400);
		
		Text exit=new Text(0,0,mmFont,"返回"){
			@SuppressLint("NewApi")
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				// TODO Auto-generated method stub
				switch (pSceneTouchEvent.getAction()) {
				case TouchEvent.ACTION_DOWN:
					mMenuScene.clearChildScene();
					mMenuScene.setChildScene(mainScene);
					MainActivity.this.runOnUpdateThread(new Runnable() {
						@Override
						public void run() {
							msetupScene.detachSelf();
						}
					});
					mEngine.setScene(mMenuScene);
					break;
				case TouchEvent.ACTION_UP:
					break;
				}
				return true;
			}
		};
		onesize.setPosition((CAMERA_WIDTH-onesize.getWidth())/2, 0);
		othersize.setPosition((CAMERA_WIDTH-othersize.getWidth())/2, 100);
		soundsetting.setPosition((CAMERA_WIDTH-soundsetting.getWidth())/2, 400);
		oner.setColor(0, 1, 0);
		otherr.setColor(0, 1, 0);
		soundsetting.setColor(0, 0, 1);
		onear.setColor(0, 1, 0);
		otherar.setColor(0, 0, 1);
		oner.setPosition(250, 0);
		otherr.setPosition(250, 100);
		onear.setPosition(250, 200);
		otherar.setPosition(250, 300);
		oneanimation.setPosition((CAMERA_WIDTH-oneanimation.getWidth())/2, 200);
		otheranimation.setPosition((CAMERA_WIDTH-otheranimation.getWidth())/2, 300);
		msetupScene.registerTouchArea(oner);
		msetupScene.registerTouchArea(otherr);
		msetupScene.registerTouchArea(onear);
		msetupScene.registerTouchArea(otherar);
		msetupScene.registerTouchArea(exit);
		msetupScene.registerTouchArea(soundsetting);
		msetupScene.attachChild(oner);
		msetupScene.attachChild(otherr);
		msetupScene.attachChild(soundsetting);
		msetupScene.attachChild(soundtext);
		msetupScene.attachChild(onesize);
		msetupScene.attachChild(othersize);
		msetupScene.attachChild(onear);
		msetupScene.attachChild(otherar);
		msetupScene.attachChild(oneanimation);
		msetupScene.attachChild(otheranimation);
		msetupScene.attachChild(exit);
	}
	public void creatGameScene(){
		//根据设置的字体大小设置大小和位置,小孩字体
		if (audioOptions.getInt("kidfontsize", 1) == 1) {
			kidfontsize=40;
		}
		else if(audioOptions.getInt("kidfontsize", 1) == 2)
		{
			kidfontsize=50;
		}
		else
		{
			kidfontsize=80;
		}
		//老人字体
		if (audioOptions.getInt("oldfontsize", 1) == 1) {
			oldfontsize=40;
		}
		else if(audioOptions.getInt("oldfontsize", 1) == 2)
		{
			oldfontsize=50;
		}
		else
		{
			oldfontsize=80;
		}
		//小孩动画
		if (audioOptions.getInt("kidanimation", 1) == 1) {
			kidan=0;
		}
		else if(audioOptions.getInt("kidanimation", 1) == 2)
		{
			kidan=1;
		}
		else
		{
			kidan=0.5f;
		}
		//老人动画
		if (audioOptions.getInt("oldanimation", 1) == 1) {
			oldan=0;
		}
		else if(audioOptions.getInt("oldanimation", 1) == 2)
		{
			oldan=1;
		}
		else
		{
			oldan=0.5f;
		}
		mStaticMenuScene=new Scene(2);
		endScene = new Scene(2);
		backimg1=new Rectangle(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		backimg1.setColor(1f, 1f, 1f);
		mStaticMenuScene.attachChild(backimg1);
		/*
		//创造物理世界
		mPhysicsWorld = new PhysicsWorld(new Vector2(0,SensorManager.GRAVITY_EARTH), false);
		FixtureDef wallFixture = PhysicsFactory.createFixtureDef(0, 0, 0);
		Rectangle floor = new Rectangle(0, CAMERA_HEIGHT - 2, CAMERA_WIDTH, 2);
		PhysicsFactory.createBoxBody(mPhysicsWorld, floor, BodyType.StaticBody, wallFixture);
		mStaticMenuScene.attachChild(floor);
		mStaticMenuScene.registerUpdateHandler(mPhysicsWorld);
*/
		midline=new Line(CAMERA_WIDTH/2, 0, CAMERA_WIDTH/2, CAMERA_HEIGHT);
		midline.setColor(0, 0, 0);
		kidtext=new ChangeableText(0, 0, mmFont, "wait",6);
		oldtext=new ChangeableText(0, 0, mmFont, "action",6);
		kidtext.setPosition(200, (CAMERA_HEIGHT-kidtext.getHeight())/2);
		oldtext.setPosition(CAMERA_WIDTH-oldtext.getWidth()-200, (CAMERA_HEIGHT-kidtext.getHeight())/2);
		kidtext.setRotation(90);
		oldtext.setRotation(-90);
		//小孩目标区
		for(int i=0;i<6;i++)
		{
			leftr[i]=new Rectangle(0, 0, kidfontsize, kidfontsize);
			leftr[i].setVisible(false);
			mStaticMenuScene.attachChild(leftr[i]);
			rightr[i]=new Rectangle(0, 0, oldfontsize, oldfontsize);
			rightr[i].setVisible(false);
			mStaticMenuScene.attachChild(rightr[i]);
		}
		for(int what=0;what<6;what++)
		{
			leftobb[what]=new Sprite(0,0,kidfontsize,kidfontsize,wordTextureRegion[what]){
				//为卡牌绑定监听
				@Override
				public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
						float pTouchAreaLocalX, float pTouchAreaLocalY) {
					// TODO Auto-generated method stub
					if(who==false)
					{
							switch(pSceneTouchEvent.getAction()){
							case TouchEvent.ACTION_DOWN:
								//根据该精灵的userdata(0)（存放着字母的asc码值）播放相应字母
								int temp=(Integer) ((ArrayList)this.getUserData()).get(0);
								mSoundplay(lettersound[temp]);
								//动画停止
								this.clearEntityModifiers();
								this.setAlpha(1f);
								//将除了自己以外的所有字母隐藏
								for(int i=0;i<precolor.length();i++)
								{
									if(((ArrayList)this.getUserData()).get(1).equals(((ArrayList)leftobb[i].getUserData()).get(1)))
									{
										leftobb[i].setVisible(true);
									}
									else
									{
										//如果第i个已经在正确的位置则不隐藏
										if(!isfill[i])
										{
										leftobb[i].setVisible(false);
										}
									}
								}
								Toast.makeText(MainActivity.this,"card touch down", Toast.LENGTH_SHORT).show();
								break;
							case TouchEvent.ACTION_UP:
								boolean biaoji=false;
								for(int j=0;j<precolor.length();j++)
								{
									int left1=(int)this.getX();
									int right1=left1+(int)this.getWidth();
									int top1=(int)this.getY();
									int bottom=top1+(int)this.getHeight();
									//判断该字母与所有空挡是否有碰触，如果有（判读setUserData是否一致，如果一致则正确，不一致提示错误，作出错误处理）如果所有的都没碰触，退回原位
									if(iscoss(left1,right1,top1,bottom,(int)leftr[j].getX(),(int)(leftr[j].getX()+leftr[j].getWidth()),(int)leftr[j].getY(),(int)(leftr[j].getY()+leftr[j].getHeight())))
									{
										//如果第j个已经被填充，按错误处理
										if(isfill[j])
										{
											break;
										}
										//有触碰
										if(((ArrayList)this.getUserData()).get(0).equals(leftr[j].getUserData()))
										{
											//正确
											Toast.makeText(MainActivity.this,"正确"+this.getX()+this.getY(), Toast.LENGTH_SHORT).show();
											this.setPosition((CAMERA_WIDTH)/2-this.getWidth()-movetopl, j*CAMERA_HEIGHT/precolor.length()+kidHeiW);
											mStaticMenuScene.unregisterTouchArea(this);
											biaoji=true;
											//将老人对应的牌放到相应位置
											rightobb[(Integer) ((ArrayList)this.getUserData()).get(1)].setAlpha(1);
											rightobb[(Integer) ((ArrayList)this.getUserData()).get(1)].clearEntityModifiers();
											rightobb[(Integer) ((ArrayList)this.getUserData()).get(1)].setPosition((CAMERA_WIDTH)/2+movetopl, (precolor.length()-j-1)*CAMERA_HEIGHT/precolor.length()+oldHeiW);
											mStaticMenuScene.unregisterTouchArea(rightobb[(Integer) ((ArrayList)this.getUserData()).get(1)]);
											//第i个被填充
											isfill[j]=true;
											kidtext.setText("wait");
											oldtext.setText("action");
											who=true;
											if(++step>=precolor.length())
											{
												mSoundplay(passsound);
												clear();
												refresth();
											}
											break;
										}
										else
										{
											mSoundplay(errorsound);
											//错误
											Toast.makeText(MainActivity.this,"错误"+this.getX()+this.getY(), Toast.LENGTH_SHORT).show();
										}
										break;
									}
								}
								if(biaoji==false)
								{
								//退回原位
								this.setPosition(movebottoml, randomarray[(Integer) ((ArrayList)this.getUserData()).get(1)]*CAMERA_HEIGHT/precolor.length()+kidHeiW);
								Toast.makeText(MainActivity.this,"card touch up"+this.getX()+this.getY(), Toast.LENGTH_SHORT).show();
								}
								//将所有字母显示
								for(int i=0;i<precolor.length();i++)
								{
									leftobb[i].setVisible(true);
								}
								break;
							case TouchEvent.ACTION_MOVE:
								if(pSceneTouchEvent.getX()<CAMERA_WIDTH/2)
								{
								this.setPosition(pSceneTouchEvent.getX()-this.getWidth()/2,pSceneTouchEvent.getY()-this.getHeight()/2);
								}
								break;
							}
					}
					return true;	
				}
			};
			rightobb[what]=new Sprite(0,0,oldfontsize,oldfontsize,wordTextureRegion[what+6]){
				//为卡牌绑定监听
				@Override
				public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
						float pTouchAreaLocalX, float pTouchAreaLocalY) {
					// TODO Auto-generated method stub
					if(who==true)
					{
							switch(pSceneTouchEvent.getAction()){
							case TouchEvent.ACTION_DOWN:
								//根据该精灵的userdata(0)（存放着字母的asc码值）播放相应字母
								int temp=(Integer) ((ArrayList)this.getUserData()).get(0);
								mSoundplay(lettersound[temp]);
								//动画停止
								this.clearEntityModifiers();
								this.setAlpha(1f);
								//将除了自己以外的所有字母隐藏
								for(int i=0;i<precolor.length();i++)
								{
									if(((ArrayList)this.getUserData()).get(1).equals(((ArrayList)rightobb[i].getUserData()).get(1)))
									{
										rightobb[i].setVisible(true);
									}
									else
									{
										//如果第i个已经在正确的位置则不隐藏
										if(!isfill[i])
										{
										rightobb[i].setVisible(false);
										}
									}
								}
								Toast.makeText(MainActivity.this,"card touch down", Toast.LENGTH_SHORT).show();
								break;
							case TouchEvent.ACTION_UP:
								boolean biaoji=false;
								for(int j=0;j<precolor.length();j++)
								{
									int left1=(int)this.getX();
									int right1=left1+(int)this.getWidth();
									int top1=(int)this.getY();
									int bottom=top1+(int)this.getHeight();
									//判断该字母与所有空挡是否有碰触，如果有（判读setUserData是否一致，如果一致则正确，不一致提示错误，作出错误处理）如果所有的都没碰触，退回原位
									if(iscoss(left1,right1,top1,bottom,(int)rightr[j].getX(),(int)(rightr[j].getX()+rightr[j].getWidth()),(int)rightr[j].getY(),(int)(rightr[j].getY()+rightr[j].getHeight())))
									{
										//如果第j个已经被填充，按错误处理
										if(isfill[j])
										{
											break;
										}
										//有触碰
										if(((ArrayList)this.getUserData()).get(0).equals(rightr[j].getUserData()))
										{
											//正确
											Toast.makeText(MainActivity.this,"正确"+this.getX()+this.getY(), Toast.LENGTH_SHORT).show();
											this.setPosition((CAMERA_WIDTH)/2+movetopl, (precolor.length()-1-j)*CAMERA_HEIGHT/precolor.length()+oldHeiW);
											mStaticMenuScene.unregisterTouchArea(this);
											biaoji=true;
											//将小孩牌对应的牌放到相应位置
											leftobb[(Integer) ((ArrayList)this.getUserData()).get(1)].setAlpha(1);
											leftobb[(Integer) ((ArrayList)this.getUserData()).get(1)].clearEntityModifiers();
											leftobb[(Integer) ((ArrayList)this.getUserData()).get(1)].setPosition((CAMERA_WIDTH)/2-leftobb[(Integer) ((ArrayList)this.getUserData()).get(1)].getWidth()-movetopl, j*CAMERA_HEIGHT/precolor.length()+kidHeiW);
											mStaticMenuScene.unregisterTouchArea(leftobb[(Integer) ((ArrayList)this.getUserData()).get(1)]);
											kidtext.setText("action");
											oldtext.setText("wait");
											isfill[j]=true;
											who=false;
											if(++step>=precolor.length())
											{
												mSoundplay(passsound);
												clear();
												refresth();
											}
											break;
										}
										else
										{
											mSoundplay(errorsound);
											//错误
											Toast.makeText(MainActivity.this,"错误"+this.getX()+this.getY(), Toast.LENGTH_SHORT).show();
										}
										break;
									}
								}
								if(biaoji==false)
								{
								//退回原位
								this.setPosition(CAMERA_WIDTH-movebottoml-this.getWidth(), randomarray2[(Integer)((ArrayList)this.getUserData()).get(1)]*CAMERA_HEIGHT/precolor.length()+oldHeiW);
								Toast.makeText(MainActivity.this,"card touch up"+this.getX()+this.getY(), Toast.LENGTH_SHORT).show();
								}
								//将所有字母显示
								for(int i=0;i<precolor.length();i++)
								{
									rightobb[i].setVisible(true);
								}
								break;
							case TouchEvent.ACTION_MOVE:
								if(pSceneTouchEvent.getX()>CAMERA_WIDTH/2){
								this.setPosition(pSceneTouchEvent.getX()-this.getWidth()/2,pSceneTouchEvent.getY()-this.getHeight()/2);
								}
								break;
							}
					}
					return true;
				}
			};
			leftobb[what].setVisible(false);
			rightobb[what].setVisible(false);
			mStaticMenuScene.attachChild(leftobb[what]);
			mStaticMenuScene.attachChild(rightobb[what]);
		}
		refresh=new Sprite(375,55,50,50,refreshTextureRegion){
			@SuppressLint("NewApi")
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				// TODO Auto-generated method stub
				switch (pSceneTouchEvent.getAction()) {
				case TouchEvent.ACTION_DOWN:
					clear();
					refresth();
					break;
				case TouchEvent.ACTION_UP:
					break;
				}
				return true;
			}
		};
		playmusic=new Sprite(375,215,50,50,playsoundTextureRegion){
			@SuppressLint("NewApi")
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				// TODO Auto-generated method stub
				switch (pSceneTouchEvent.getAction()) {
				case TouchEvent.ACTION_DOWN:
					mSoundplay(colorsound[preparezimu.what]);
					break;
				case TouchEvent.ACTION_UP:
					break;
				}
				return true;
			}
		};
		goback=new Sprite(375,375,50,50, gobackTextureRegion){
			@SuppressLint("NewApi")
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				// TODO Auto-generated method stub
				switch (pSceneTouchEvent.getAction()) {
				case TouchEvent.ACTION_DOWN:
					mMenuScene.clearChildScene();
					mMenuScene.setChildScene(mainScene);
					MainActivity.this.runOnUpdateThread(new Runnable() {
						@Override
						public void run() {
							mStaticMenuScene.detachSelf();
						}
					});
					mEngine.setScene(mMenuScene);
					break;
				case TouchEvent.ACTION_UP:
					break;
				}
				return true;
			}
		};
		//分数
		myscoretextkid=new ChangeableText(0, 0, mscoreFont, "Complete:0",12);
		myscoretextold=new ChangeableText(0, 0, mscoreFont, "Complete:0",12);
		myscoretextkid.setPosition(200, 100);
		myscoretextkid.setRotation(90);
		myscoretextold.setPosition(450, 360);
		myscoretextold.setRotation(-90);
		mStaticMenuScene.attachChild(myscoretextkid);
		mStaticMenuScene.attachChild(myscoretextold);
		
		mStaticMenuScene.registerTouchArea(refresh);
		mStaticMenuScene.registerTouchArea(playmusic);
		mStaticMenuScene.registerTouchArea(goback);
		mStaticMenuScene.attachChild(kidtext);
		mStaticMenuScene.attachChild(oldtext);
		mStaticMenuScene.setTouchAreaBindingEnabled(true);
		mStaticMenuScene.attachChild(midline);
		mStaticMenuScene.attachChild(refresh);
		mStaticMenuScene.attachChild(playmusic);
		mStaticMenuScene.attachChild(goback);
		//刷新数据
		refresth();
	}
	public void addscore(int i){
		myscore=myscore+i;
		final Text addscore1=new Text(0, 0, mmFont, "+"+i,HorizontalAlign.CENTER);
		addscore1.setRotation(90);
		addscore1.registerEntityModifier(new SequenceEntityModifier(
				new IEntityModifierListener() {
					@Override
					public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
						// TODO Auto-generated method stub
						addscore1.setVisible(false);
						myscoretextkid.setText("Complete:"+myscore);
						MainActivity.this.runOnUpdateThread(new Runnable() {
							@Override
							public void run() {
								// TODO Auto-generated method stub
								   addscore1.detachSelf();
							}
						});
					}
				},
				new ParallelEntityModifier(
				new MoveModifier(1f, (CAMERA_WIDTH/4-addscore1.getWidth()/2), myscoretextkid.getX(), (CAMERA_HEIGHT-addscore1.getHeight())/2, myscoretextkid.getY()),
				new AlphaModifier(1f, 1, 0)),
				new ScaleModifier(1f, 1f, 0)));
		final Text addscore2=new Text(0, 0, mmFont, "+"+i,HorizontalAlign.CENTER);
		addscore2.setRotation(-90);
		addscore2.registerEntityModifier(new SequenceEntityModifier(
				new IEntityModifierListener() {
					@Override
					public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
						// TODO Auto-generated method stub
						addscore2.setVisible(false);
						myscoretextold.setText("Complete:"+myscore);
						MainActivity.this.runOnUpdateThread(new Runnable() {
							@Override
							public void run() {
								// TODO Auto-generated method stub
								   addscore2.detachSelf();

							}
						});
					}
				},
				new ParallelEntityModifier(
				new MoveModifier(1f, (CAMERA_WIDTH*3/4-addscore2.getWidth()/2), myscoretextold.getX(), (CAMERA_HEIGHT-addscore2.getHeight())/2, myscoretextold.getY()),
				new AlphaModifier(1f, 1, 0)),
				new ScaleModifier(1f, 1f, 0)));
		mStaticMenuScene.attachChild(addscore1);
		mStaticMenuScene.attachChild(addscore2);
		
		
	}
	protected void refresth() {
		//每次完成，完成数加1
		addscore(1);
		// TODO Auto-generated method stub	
		kidtext.setText("wait");
		oldtext.setText("action");
		who=true;
		//个数重置
		step=0;
		//preparezimu.color[0]当局单词
		preparezimu.random();
		precolor=preparezimu.getonecolor();
		mSoundplay(colorsound[preparezimu.what]);
		//根据单词个数，调整目标位置
		setobbpostion(precolor.length());
		//小孩目标区
		for(int i=0;i<precolor.length();i++)
		{
			leftr[i].setColor(255/255, 192/255, 203/255);
			leftr[i].setVisible(true);
			leftr[i].setUserData(((int)precolor.toString().charAt(i)-97));
			leftr[i].setPosition((CAMERA_WIDTH)/2-leftr[i].getWidth()-movetopl, i*CAMERA_HEIGHT/precolor.length()+kidHeiW);
			isfill[i]=false;
		}
		//随机排列数组
		randomarray=new int[precolor.length()];
		randomarray2=new int[precolor.length()];
		for(int i=0;i<precolor.length();i++)
		{
			randomarray[i]=i;
		}
		//洗牌程序
		for(int i=0;i<randomarray.length;i++)
		{
			swap(randomarray,i,random.nextInt(randomarray.length));
		}
		//大人跟小孩相反
		for(int i=0;i<randomarray.length;i++)
		{
			randomarray2[i]=randomarray.length-randomarray[i]-1;
		}
		//小孩的牌
		for(int i=0;i<precolor.length();i++)
		{
			ArrayList cun=new ArrayList();
			//字母的asc
			cun.add((int)precolor.toString().charAt(i)-97);
			//在本来位置
			cun.add(i);
			//换纹理
			changetextureregion(false,i,(Integer) cun.get(0));
			leftobb[i].setVisible(true);
			leftobb[i].setUserData(cun);
			final int dangqian=i;
			leftobb[i].registerEntityModifier(new SequenceEntityModifier(
					new IEntityModifierListener() {
						@Override
						public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
							// TODO Auto-generated method stub
							mStaticMenuScene.registerTouchArea(leftobb[dangqian]);
							//有动画
							if(kidan!=0)
							{
							//播放动画
							leftobb[dangqian].registerEntityModifier(new LoopEntityModifier(
									new SequenceEntityModifier(
									new ParallelEntityModifier(
									new MoveXModifier(kidan, movebottoml, 200),
									new AlphaModifier(kidan, 1, 0.2f)),
									new ParallelEntityModifier(
											new MoveXModifier(kidan, 200,movebottoml),
											new AlphaModifier(kidan, 0.2f, 1))
									)
									));
							}
						}
					},
					new AlphaModifier(2f, 0f, 1f),new ParallelEntityModifier(new MoveModifier(1, (CAMERA_WIDTH)/2-leftobb[i].getWidth()-movetopl, movebottoml, i*CAMERA_HEIGHT/precolor.length()+kidHeiW, randomarray[i]*CAMERA_HEIGHT/precolor.length()+kidHeiW
					))));
			leftobb[i].setPosition((CAMERA_WIDTH)/2-leftobb[i].getWidth()-movetopl, i*CAMERA_HEIGHT/precolor.length()+kidHeiW);
			leftobb[i].setRotation(90);
			
		}
		//老人目标区
		for(int i=0;i<precolor.length();i++)
		{
			rightr[i].setColor(255/255, 192/255, 203/255);
			rightr[i].setVisible(true);
			rightr[i].setUserData(((int)precolor.toString().charAt(i)-97));
			rightr[i].setPosition((CAMERA_WIDTH)/2+movetopl, (precolor.length()-1-i)*CAMERA_HEIGHT/precolor.length()+oldHeiW);
		}
		//老人的牌
		for(int i=0;i<precolor.length();i++)
		{
			ArrayList cun=new ArrayList();
			//字母的asc
			cun.add((int)precolor.toString().charAt(i)-97);
			//在打乱序列位置
			cun.add(i);
			changetextureregion(true,i,(Integer) cun.get(0));
			
			rightobb[i].setUserData(cun);
			rightobb[i].setVisible(true);
			final int dangqian=i;
			rightobb[i].registerEntityModifier(new SequenceEntityModifier(
					new IEntityModifierListener() {
						@Override
						public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
							// TODO Auto-generated method stub
							mStaticMenuScene.registerTouchArea(rightobb[dangqian]);
							if(oldan!=0)
							{
							rightobb[dangqian].registerEntityModifier(new LoopEntityModifier(
									new SequenceEntityModifier(
									new ParallelEntityModifier(new MoveXModifier(oldan, CAMERA_WIDTH-rightobb[dangqian].getWidth()-movebottoml, 600),new AlphaModifier(oldan, 1, 0.2f)),
									new ParallelEntityModifier(new MoveXModifier(oldan, 600, CAMERA_WIDTH-rightobb[dangqian].getWidth()-movebottoml),new AlphaModifier(oldan, 0.2f, 1))
									)
									));
							}
						}
					},
					new AlphaModifier(2f, 0f, 1f),new ParallelEntityModifier(new MoveModifier(1, (CAMERA_WIDTH)/2+movetopl, CAMERA_WIDTH-rightobb[i].getWidth()-movebottoml, (precolor.length()-i-1)*CAMERA_HEIGHT/precolor.length()+oldHeiW, randomarray2[i]*CAMERA_HEIGHT/precolor.length()+oldHeiW
					))));
			rightobb[i].setPosition((CAMERA_WIDTH)/2+movetopl, (precolor.length()-i-1)*CAMERA_HEIGHT/precolor.length()+oldHeiW);
			rightobb[i].setRotation(-90);
		}
	}

	private void setobbpostion(int length) {
		// TODO Auto-generated method stub
		switch(length){
		case 3:
			//老人小字体
			if(oldfontsize==40)
			{
				oldHeiW=60;
			}//老人中字体
			else if(oldfontsize==50)
			{
				oldHeiW=55;
			}//老人大字体
			else
			{
				oldHeiW=40;
			}
			//小孩小字体
			if(kidfontsize==40)
			{
				kidHeiW=60;
			}//小孩中字体
			else if(kidfontsize==50)
			{
				kidHeiW=55;
			}//小孩大字体
			else
			{
				kidHeiW=40;
			}
			break;
		case 4:
			//老人小字体
			if(oldfontsize==40)
			{
				oldHeiW=40;
			}//老人中字体
			else if(oldfontsize==50)
			{
				oldHeiW=35;
			}//老人大字体
			else 
			{
				oldHeiW=20;
			}
			//小孩小字体
			if(kidfontsize==40)
			{
				kidHeiW=40;
			}//小孩中字体
			else if(kidfontsize==50)
			{
				kidHeiW=35;
			}//小孩大字体
			else
			{
				kidHeiW=20;
			}
			break;
		case 5:
			//老人小字体
			if(oldfontsize==40)
			{
				oldHeiW=28;
			}//老人中字体
			else if(oldfontsize==50)
			{
				oldHeiW=23;
			}//老人大字体
			else
			{
				oldHeiW=8;
			}
			//小孩小字体
			if(kidfontsize==40)
			{
				kidHeiW=28;
			}//小孩中字体
			else if(kidfontsize==50)
			{
				kidHeiW=23;
			}//小孩大字体
			else
			{
				kidHeiW=8;
			}
			break;
		case 6:
			//老人小字体
			if(oldfontsize==40)
			{
				oldHeiW=20;
			}//老人中字体
			else if(oldfontsize==50)
			{
				oldHeiW=15;
			}//老人大字体
			else
			{
				oldHeiW=0;
			}
			//小孩小字体
			if(kidfontsize==40)
			{
				kidHeiW=20;
			}//小孩中字体
			else if(kidfontsize==50)
			{
				kidHeiW=15;
			}//小孩大字体
			else
			{
				kidHeiW=0;
			}
			break;
		
		}
		
	}

	private void changetextureregion(boolean b, int i, Integer integer) {
		// TODO Auto-generated method stub
		//b==true为小孩,i表示第几个,integer表示换成什么
		if(b)
		{
			wordTextureRegion[i].setTexturePosition((integer%8)*128,(integer/8)*128);
		}
		else{
			wordTextureRegion[i+6].setTexturePosition((integer%8)*128,(integer/8)*128);
		}
	}

	protected void clear() {
		// TODO Auto-generated method stub
		for(int i=0;i<precolor.length();i++)
		{
			rightobb[i].clearEntityModifiers();
			rightobb[i].setVisible(false);
			leftobb[i].setVisible(false);
			leftobb[i].clearEntityModifiers();
			leftr[i].setVisible(false);
			rightr[i].setVisible(false);
		}
	}

	private void swap(int[] randomarray, int i, int nextInt) {
		// TODO Auto-generated method stub
		int temp=randomarray[i];
		randomarray[i]=randomarray[nextInt];
		randomarray[nextInt]=temp;
	}
	private boolean iscoss(int left1,int right1,int top1,int bottom1,int left2,int right2,int top2,int bottom2){
		if(right1 < left2) return false;
		if(left1 >= right2 ) return false;
		if(bottom1 < top2 ) return false;
		if(top1 >= bottom2 ) return false;
		return true;
	}
	protected void gameInit() {
		// TODO Auto-generated method stub	
	}
	public void initSplashScene()
	{
		splashScene = new Scene(2);
		Text text=new Text(0, 0, mmFont, "Loading");
		text.setPosition((CAMERA_WIDTH-text.getWidth())/2, (CAMERA_HEIGHT-text.getHeight())/2);
		Rectangle loadingback=new Rectangle(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		splashScene.getLastChild().attachChild(loadingback);
		splashScene.getLastChild().attachChild(text);
		mMenuScene.setChildScene(splashScene);
	}
	public void loadResources()
	{
		wordTexture=new Texture(1024, 512,TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		for(int i=0;i<=25;i++)
		{
		wordTextureRegion[i]=TextureRegionFactory.createFromAsset(wordTexture, this, "main/zimu_"+(i+1)+".png",(i%8)*128,(i/8)*128);
		} 
		this.mEngine.getTextureManager().loadTexture(wordTexture);
		gobackTexture=new Texture(128, 128,TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		gobackTextureRegion=TextureRegionFactory.createFromAsset(gobackTexture, this, "main/goback.png",0,0);
		this.mEngine.getTextureManager().loadTexture(gobackTexture);
		playsoundTexture=new Texture(128, 128,TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		playsoundTextureRegion=TextureRegionFactory.createFromAsset(playsoundTexture, this, "main/playsound.png",0,0);
		this.mEngine.getTextureManager().loadTexture(playsoundTexture);
		refreshTexture=new Texture(256, 256,TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		refreshTextureRegion=TextureRegionFactory.createFromAsset(refreshTexture, this, "main/refresh.png",0,0);
		this.mEngine.getTextureManager().loadTexture(refreshTexture);
		try {
			for(int i=0;i<lettersound.length;i++)
			{
			  this.lettersound[i]=SoundFactory.createSoundFromAsset(this.mEngine.getSoundManager(), getApplicationContext(), "music/"+(char)(i+97)+".mp3");
			}
			for(int i=0;i<colorsound.length;i++)
			{
			  this.colorsound[i]=SoundFactory.createSoundFromAsset(this.mEngine.getSoundManager(), getApplicationContext(), "music/"+preparezimu.color[i].toString()+".mp3");
			}
			this.passsound=SoundFactory.createSoundFromAsset(this.mEngine.getSoundManager(), getApplicationContext(), "music/pass.mp3");
			this.errorsound=SoundFactory.createSoundFromAsset(this.mEngine.getSoundManager(), getApplicationContext(), "music/error.mp3");
			
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public void onLoadComplete() {
		// TODO Auto-generated method stub
	}
	@Override
	public void onPauseGame() {
		// TODO Auto-generated method stub
		super.onPauseGame();
	}
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			showCheckDialog("你要离开我吗?");
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	//退出提示函数
	public void showCheckDialog(String message){
		CustomDialog dialog;
		CustomDialog.Builder customBuilder = new
                CustomDialog.Builder(MainActivity.this);
               customBuilder.setTitle("退出?")
                .setMessage(message)
                .setPositiveButton("是的", 
                    new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    	setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
          	    	    ExitApplication.getInstance().exit();
                    }
                }).
                setNegativeButton("逗你玩", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    	dialog.dismiss();
                    }
                });
            dialog = customBuilder.create();
            dialog.show();
        
	}
	@Override
	public void onResumeGame() {
		// TODO Auto-generated method stub
		super.onResumeGame();
	}
	private void mSoundplay(Sound mSound)
	{
		if(audioOptions.getBoolean("effectsOn", true))
		{
			mSound.play();
		}
	}
	private Runnable begainpushhandler=new Runnable(){
		@Override
		public void run() {
			// TODO Auto-generated method stub
			timer = new Timer();
		    task = new TimerTask() {
				@Override
				public void run() {
					mHandler.sendEmptyMessage(MSG_COUNTER_DOWN);
				}
			};
			timer.schedule(task, 0, UPDATE_INTERVAL);
			isbegin=true;
		}	
	};
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	private void setGameOverScence(){
		mStaticMenuScene.setChildScene(endScene);
	}            
}
