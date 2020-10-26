package ru.mygame.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.mygame.base.Sprite;
import ru.mygame.math.Rect;

public class Picture extends Sprite {

    private Vector2 touch;
    private Vector2 speed;
    private Vector2 tmp;

    public Picture(TextureRegion region) {
        super(region);
        touch = new Vector2();
        speed = new Vector2();
        tmp = new Vector2();
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.2f);
        pos.set(worldBounds.pos);
    }

    @Override
    public void update(float delta) {
        tmp.set(touch);
        if(tmp.sub(pos).len() <= speed.len()){
            pos.set(touch);
        } else {
            pos.add(speed);
        }
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        this.touch.set(touch);
        speed.set(touch.cpy().sub(pos).setLength(0.01f));
        return false;
    }
}
