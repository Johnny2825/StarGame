package ru.mygame.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;

import java.util.List;

import ru.mygame.base.BaseScreen;
import ru.mygame.math.Rect;
import ru.mygame.pool.BulletPool;
import ru.mygame.pool.EnemyShipPool;
import ru.mygame.pool.ExplosionPool;
import ru.mygame.sprite.Background;
import ru.mygame.sprite.Bullet;
import ru.mygame.sprite.EnemyShip;
import ru.mygame.sprite.MainShip;
import ru.mygame.sprite.MessageGameOver;
import ru.mygame.sprite.Star;
import ru.mygame.sprite.TrackingStar;
import ru.mygame.utils.EnemyEmitter;
import ru.mygame.utils.Font;

public class GameScreen extends BaseScreen {

    private static final int STAR_COUNT = 64;
    private static final float FONT_SIZE = 0.02f;
    private static final float MARGIN = 0.015f;

    private Game game;

    private enum State { PLAYING, GAME_OVER }

    private static final String FRAGS = "FRAGS: ";
    private static final String HP = "HP: ";
    private static final String LEVEL = "LEVEL: ";

    private TextureAtlas atlas;
    private Texture bg;
    private Sound enemyBulletSound;
    private Sound explosionSound;

    private Background background;
    private Star[] stars;
    private BulletPool bulletPool;
    private EnemyShipPool enemyShipPool;
    private ExplosionPool explosionPool;
    private MainShip mainShip;
    private EnemyEmitter enemyEmitter;
    private MessageGameOver messageGameOver;

    private State state;

    private int frags;

    private Font font;
    private StringBuilder sbFrags;
    private StringBuilder sbHp;
    private StringBuilder sbLevel;
    private String message = "PRESS ANY KEY TO CONTINUE";

    public GameScreen(Game game) {
        super();
        this.game = game;
    }

    @Override
    public void show() {
        super.show();
        atlas = new TextureAtlas("textures/mainAtlas.tpack");
        bg = new Texture("textures/bg.jpg");
        enemyBulletSound = Gdx.audio.newSound(Gdx.files.internal("sounds/bullet.wav"));
        explosionSound = Gdx.audio.newSound(Gdx.files.internal("sounds/explosion.wav"));

        background = new Background(bg);

        bulletPool = new BulletPool();
        explosionPool = new ExplosionPool(atlas, explosionSound);
        enemyShipPool = new EnemyShipPool(bulletPool, explosionPool, worldBounds);
        mainShip = new MainShip(atlas, bulletPool, explosionPool);
        enemyEmitter = new EnemyEmitter(worldBounds, enemyShipPool, enemyBulletSound, atlas);

        stars = new TrackingStar[STAR_COUNT];
        for (int i = 0; i < STAR_COUNT; i++) {
            stars[i] = new TrackingStar(atlas, mainShip.getV());
        }

        messageGameOver = new MessageGameOver(atlas);

        font = new Font("font/font.fnt", "font/font.png");
        font.setSize(FONT_SIZE);
        sbFrags = new StringBuilder();
        sbHp = new StringBuilder();
        sbLevel = new StringBuilder();

        state = State.PLAYING;
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
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        for (Star star : stars) {
            star.resize(worldBounds);
        }
        mainShip.resize(worldBounds);
    }

    @Override
    public void dispose() {
        bg.dispose();
        atlas.dispose();
        bulletPool.dispose();
        enemyShipPool.dispose();
        explosionPool.dispose();
        enemyBulletSound.dispose();
        explosionSound.dispose();
        mainShip.dispose();
        super.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        if (state == State.PLAYING) {
            mainShip.keyDown(keycode);
        } else if (state == State.GAME_OVER){
            game.setScreen(new GameOverScreen(game));
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (state == State.PLAYING) {
            mainShip.keyUp(keycode);
        }
        return false;
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        if (state == State.PLAYING) {
            mainShip.touchDown(touch, pointer, button);
        }
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        if (state == State.PLAYING) {
            mainShip.touchUp(touch, pointer, button);
        } else if (state == State.GAME_OVER){
            game.setScreen(new GameOverScreen(game));
        }
        return false;
    }

    private void update(float delta) {
        for (Star star : stars) {
            star.update(delta);
        }
        explosionPool.updateActiveSprites(delta);
        bulletPool.updateActiveSprites(delta);
        enemyShipPool.updateActiveSprites(delta);
        enemyEmitter.generate(delta, frags);
        if (state == State.PLAYING) {
            mainShip.update(delta);
        }
    }


    private void checkCollision() {
        if (state == State.GAME_OVER) {
            return;
        }
        List<EnemyShip> enemyShipList = enemyShipPool.getActiveObjects();
        for (EnemyShip enemyShip : enemyShipList) {
            if (enemyShip.isDestroyed()) {
                continue;
            }
            float minDist = mainShip.getHalfWidth() + enemyShip.getHalfWidth();
            if (enemyShip.pos.dst(mainShip.pos) < minDist) {
                enemyShip.destroy();
                mainShip.damage(enemyShip.getDamage());
                if (mainShip.isDestroyed()) {
                    state = State.GAME_OVER;
                }
                return;
            }
        }
        List<Bullet> bulletList = bulletPool.getActiveObjects();
        for (Bullet bullet : bulletList) {
            if (bullet.isDestroyed()) {
                continue;
            }
            if (bullet.getOwner() != mainShip) {
                if (mainShip.isBulletCollision(bullet)) {
                    mainShip.damage(bullet.getDamage());
                    if (mainShip.isDestroyed()) {
                        state = State.GAME_OVER;
                    }
                    bullet.destroy();
                    return;
                }
                continue;
            }
            for (EnemyShip enemyShip : enemyShipList) {
                if (enemyShip.isBulletCollision(bullet)) {
                    enemyShip.damage(bullet.getDamage());
                    if (enemyShip.isDestroyed()) {
                        frags++;
                    }
                    bullet.destroy();
                    return;
                }
            }
        }
    }



    private void freeAllDestroyed() {
        bulletPool.freeAllDestroyedActiveSprites();
        enemyShipPool.freeAllDestroyedActiveSprites();
        explosionPool.freeAllDestroyedActiveSprites();
    }

    private void draw() {
        batch.begin();
        background.draw(batch);
        for (Star star : stars) {
            star.draw(batch);
        }
        explosionPool.drawActiveSprites(batch);
        bulletPool.drawActiveSprites(batch);
        enemyShipPool.drawActiveSprites(batch);
        if (state == State.PLAYING){
            mainShip.draw(batch);
        } else if (state == State.GAME_OVER) {
            messageGameOver.draw(batch);
        }
        printInfo();
        batch.end();
    }

    private void printInfo() {
        if (state == State.GAME_OVER){
            font.draw(batch, message, worldBounds.pos.x, worldBounds.pos.y - MARGIN, Align.center);
        }
        sbFrags.setLength(0);
        font.draw(batch, sbFrags.append(FRAGS).append(frags), worldBounds.getLeft() + MARGIN, worldBounds.getTop() - MARGIN);
        sbHp.setLength(0);
        font.draw(batch, sbHp.append(HP).append(mainShip.getHp()), worldBounds.pos.x, worldBounds.getTop() - MARGIN, Align.center);
        sbLevel.setLength(0);
        font.draw(batch, sbLevel.append(LEVEL).append(enemyEmitter.getLevel()), worldBounds.getRight() - MARGIN, worldBounds.getTop() - MARGIN, Align.right);
    }
}
