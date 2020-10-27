package ru.mygame.sprite;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.mygame.base.BaseButton;
import ru.mygame.math.Rect;
import ru.mygame.screen.GameScreen;

public class PlayButton extends BaseButton {

    private final Game game;

    private static final float MARGIN = 0.05f;

    public PlayButton(TextureAtlas atlas, Game game) {
        super(atlas.findRegion("btPlay"));
        this.game = game;
    }

    @Override
    public void action() {
        game.setScreen(new GameScreen());
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.19f);
        setLeft(worldBounds.getLeft() + MARGIN);
        setBottom(worldBounds.getBottom() + MARGIN);
    }
}
