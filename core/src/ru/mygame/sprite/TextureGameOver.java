package ru.mygame.sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.mygame.base.Sprite;
import ru.mygame.math.Rect;

public class TextureGameOver extends Sprite {

    private static final float MARGIN = 0.25f;

    public TextureGameOver(TextureAtlas atlas) {
        super(atlas.findRegion("message_game_over"));
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.08f);
        setBottom(worldBounds.getTop() - MARGIN);
    }
}
