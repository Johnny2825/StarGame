package ru.mygame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import ru.mygame.base.BaseScreen;

public class MenuScreen extends BaseScreen {

    private Texture img;
    private Vector2 pos;
    private Vector2 speed;
    private Vector2 newPos;
    private Vector2 tmp;

    @Override
    public void show() {
        super.show();
        img = new Texture("sun.jpg");
        pos = new Vector2(100, 100);
        newPos = pos.cpy();
        speed = new Vector2(0, 0);
        tmp = new Vector2();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        batch.begin();
        batch.draw(img, pos.x, pos.y);
        batch.end();
        tmp.set(newPos);
        if (tmp.sub(pos).len() <= speed.len()){
            speed.set(newPos);
        } else {
            pos.add(speed);
        }

    }

    @Override
    public void dispose() {
        super.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        if (Input.Keys.SPACE != keycode) {
            return super.keyDown(keycode);
        }
        if (speed.isZero()) {
            speed.set(newPos.sub(pos)).nor().scl(4);
        } else {
            speed.setZero();
        }
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        newPos.set(screenX - (img.getWidth() / 2), Gdx.graphics.getHeight() - screenY - (img.getHeight() / 2));
        speed.set(newPos.cpy().sub(pos));
        speed.nor().scl(4);

        return false;
    }
}
