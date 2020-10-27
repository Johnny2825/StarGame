package ru.mygame.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.mygame.base.BaseButton;
import ru.mygame.math.Rect;

public class ExitButton extends BaseButton {
    private static final float MARGIN = 0.05f;


    public ExitButton(TextureAtlas atlas) {
        super(atlas.findRegion("btExit"));
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.15f);
        setRight(worldBounds.getRight() - MARGIN);
        setBottom(worldBounds.getBottom() + MARGIN);
    }

    @Override
    public void action() {
        Gdx.app.exit();
    }
}
