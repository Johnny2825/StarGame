package ru.mygame.screen;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.mygame.base.BaseScreen;
import ru.mygame.math.Rect;
import ru.mygame.sprite.Background;
import ru.mygame.sprite.Picture;

public class MenuScreen extends BaseScreen {

    private Texture bg;
    private Texture pic;
    private Background background;
    private Picture picture;

    @Override
    public void show() {
        super.show();
        bg = new Texture("textures/bg.jpg");
        background = new Background(new TextureRegion(bg));
        pic = new Texture("textures/sun.jpg");
        picture = new Picture(new TextureRegion(pic));

    }

    @Override
    public void render(float delta) {
        super.render(delta);
        picture.update(delta);

        batch.begin();
        background.draw(batch);
        picture.draw(batch);
        batch.end();
    }

    @Override
    public void resize(Rect worldBounds) {
        background.resize(worldBounds);
        picture.resize(worldBounds);
    }

    @Override
    public void dispose() {
        pic.dispose();
        bg.dispose();
        super.dispose();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        picture.touchDown(touch, pointer, button);
        return false;
    }

}
