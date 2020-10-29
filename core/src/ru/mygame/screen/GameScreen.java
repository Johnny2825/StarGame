package ru.mygame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.mygame.base.BaseScreen;
import ru.mygame.math.Rect;
import ru.mygame.pool.BulletPool;
import ru.mygame.sprite.Background;
import ru.mygame.sprite.MainShip;
import ru.mygame.sprite.Star;

public class GameScreen extends BaseScreen {

    private static final int STAR_COUNT = 64;

    private Texture bg;
    private Background background;
    private TextureAtlas atlas;
    private MainShip mainShip;
    private Star[] stars;
    private BulletPool bulletPool;

//    Sound bulletSound = Gdx.audio.newSound(Gdx.files.internal("sounds/bullet.wav"));
//    Sound exploSound = Gdx.audio.newSound(Gdx.files.internal("sounds/explosion.wav"));
    Sound laserSound = Gdx.audio.newSound(Gdx.files.internal("sounds/laser.wav"));

    public GameScreen() {
        super();
    }

    @Override
    public void show() {
        super.show();
        bg = new Texture("textures/bg.jpg");
        background = new Background(new TextureRegion(bg));
        atlas = new TextureAtlas("textures/mainAtlas.tpack");
        stars = new Star[STAR_COUNT];
        for (int i = 0; i < STAR_COUNT; i++){
            stars[i] = new Star(atlas);
        }

        bulletPool = new BulletPool();
        mainShip = new MainShip(atlas, bulletPool);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        checkCollision();
        freeAllDestroyed();
        draw();
    }

    @Override
    public void dispose() {

        laserSound.dispose();
        bulletPool.dispose();
        atlas.dispose();
        bg.dispose();
        super.dispose();
    }

    @Override
    public void resize(Rect worldBounds) {
        background.resize(worldBounds);
        for (Star star : stars){
            star.resize(worldBounds);
        }
        mainShip.resize(worldBounds);
    }

    @Override
    public boolean keyDown(int keycode) {
        mainShip.keyDown(keycode);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        mainShip.keyUp(keycode);
        return false;
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        mainShip.touchDown(touch, pointer, button);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        mainShip.touchUp(touch, pointer, button);
        return false;
    }

    private void checkCollision(){

    }

    private void freeAllDestroyed(){
        bulletPool.freeAllDestroyedActiveSprites();
    }

    private void update(float delta){
        for(Star star : stars){
            star.update(delta);
        }
        mainShip.update(delta);
        mainShip.shoot(laserSound);
        bulletPool.updateActiveSprites(delta);
    }

    private void draw(){
        batch.begin();
        background.draw(batch);
        for (Star star : stars){
            star.draw(batch);
        }
        mainShip.draw(batch);
        bulletPool.drawActiveSprites(batch);
        batch.end();
    }
}
