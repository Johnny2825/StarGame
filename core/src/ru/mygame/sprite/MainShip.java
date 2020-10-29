package ru.mygame.sprite;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.util.Timer;

import ru.mygame.base.Sprite;
import ru.mygame.math.Rect;
import ru.mygame.pool.BulletPool;

public class MainShip extends Sprite {

    private static final float SHIP_HEIGHT = 0.15f;
    private static final float MARGIN = 0.03f;
    private final Vector2 speed;
    private final Vector2 speed0;
    private final Vector2 bulletSpeed = new Vector2(0, 0.5f);
    private final Vector2 bulletPos = new Vector2();

    private Rect worldBounds;
    private BulletPool bulletPool;
    private TextureRegion bulletRegion;

    private boolean pressedLeft;
    private boolean pressedRight;

    private static final int INVALID_POINTER = -1;

    private int leftPointer = INVALID_POINTER;
    private int rightPointer = INVALID_POINTER;

    private long time = System.currentTimeMillis();

    public MainShip(TextureAtlas atlas, BulletPool bulletPool) {
        super(atlas.findRegion("main_ship"), 1, 2, 2);
        speed = new Vector2();
        speed0 = new Vector2(0.5f, 0);
        this.bulletPool = bulletPool;
        this.bulletRegion = atlas.findRegion("bulletMainShip");
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        setHeightProportion(SHIP_HEIGHT);
        setBottom(worldBounds.getBottom() + MARGIN);
    }

    @Override
    public void update(float delta){
        pos.mulAdd(speed, delta);
        if (getRight() > worldBounds.getRight()){
            setRight(worldBounds.getRight());
            stop();
        } else if (getLeft() < worldBounds.getLeft()){
            setLeft(worldBounds.getLeft());
            stop();
        }

    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.A:
            case Input.Keys.LEFT:
                pressedLeft = false;
                if (pressedRight) {
                    moveRight();
                } else {
                    stop();
                }
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                pressedRight = false;
                if (pressedLeft) {
                    moveLeft();
                } else {
                    stop();
                }
                break;
        }
        return  false;
    }

    @Override
    public boolean keyDown(int keycode) {
        System.out.println(keycode);

        switch (keycode){
            case Input.Keys.A:
            case Input.Keys.LEFT:
                moveLeft();
                break;
            case  Input.Keys.D:
                case Input.Keys.RIGHT:
                moveRight();
                break;
        }
        return false;
    }

    private void moveRight() {
        speed.set(speed0);
    }

    private void moveLeft() {
        speed.set(speed0).rotate(180);
    }

    private void stop(){
        speed.setZero();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button){
        if (touch.x <  worldBounds.pos.x) {
            if (leftPointer != INVALID_POINTER) {
                return false;
            }
            leftPointer = pointer;
            moveLeft();
        } else {
            if (rightPointer != INVALID_POINTER) {
                return false;
            }
            rightPointer = pointer;
            moveRight();
        }
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        if (pointer == leftPointer) {
            leftPointer = INVALID_POINTER;
            if (rightPointer != INVALID_POINTER) {
                moveRight();
            } else {
                stop();
            }
        } else if (pointer == rightPointer) {
            rightPointer = INVALID_POINTER;
            if (leftPointer != INVALID_POINTER) {
                moveLeft();
            } else {
                stop();
            }
        }
        return false;
    }

    public void shoot(Sound sound){
        if (System.currentTimeMillis() - time > 200){
            Bullet bullet = bulletPool.obtain();
            bulletPos.set(pos.x, getTop());
            bullet.set(this, bulletRegion, bulletPos, bulletSpeed, worldBounds, 1, 0.01f);
            sound.play(0.5f);
            time = System.currentTimeMillis();
        }
    }
}
