package ru.mygame.sprite;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.mygame.base.BaseButton;
import ru.mygame.math.Rect;
import ru.mygame.screen.GameScreen;

public class NewGameButton extends BaseButton {

    private final Game game;

    private static final float MARGIN = 0.25f;

    public NewGameButton(TextureAtlas atlas, Game game) {
        super(atlas.findRegion("button_new_game"));
        this.game = game;
    }

    @Override
    public void action() {
        game.setScreen(new GameScreen(game));
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.06f);
        setBottom(worldBounds.getTop() - MARGIN);
    }
}
