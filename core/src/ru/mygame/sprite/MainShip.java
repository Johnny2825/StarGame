package ru.mygame.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.mygame.base.Sprite;
import ru.mygame.math.Rect;

public class MainShip extends Sprite {

    private static final float MARGIN = 0.03f;
    private final Vector2 speed;
    private Rect worldBounds;

    public MainShip(TextureAtlas.AtlasRegion texture) {
        super(new TextureRegion(texture, 0, 0, texture.getRegionWidth() / 2, texture.getRegionHeight()));
        speed = new Vector2();
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        setHeightProportion(0.15f);
        setBottom(worldBounds.getBottom() + MARGIN);
    }

    @Override
    public void update(float delta) {
        pos.mulAdd(speed, delta);
        if (getRight() > worldBounds.getRight()){
            setRight(worldBounds.getRight());
        }
        if (getLeft() < worldBounds.getLeft()){
            setLeft(worldBounds.getLeft());
        }

    }


    @Override
    public boolean keyDown(int keycode) {
        System.out.println(keycode);
        if (keycode == 21 || keycode == 29){
            speed.set(-0.2f, 0).setLength(0.3f);
        }
        if (keycode == 22 || keycode == 32){
            speed.set(0.2f, 0).setLength(0.3f);
        }
        return false;
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button){
        speed.set(touch.x, 0).setLength(0.3f);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {

        return false;
    }
}
