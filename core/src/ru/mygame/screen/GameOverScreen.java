package ru.mygame.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;

import ru.mygame.base.BaseScreen;
import ru.mygame.math.Rect;
import ru.mygame.sprite.Background;
import ru.mygame.sprite.NewGameButton;
import ru.mygame.sprite.Star;
import ru.mygame.utils.Font;

public class GameOverScreen extends BaseScreen {

    private static final int STAR_COUNT = 256;
    private static final float FONT_SIZE = 0.06f;
    private static final float MARGIN = 0.1f;

    private Game game;
    private Texture bg;
    private Background background;
    private TextureAtlas atlas;
    private Font font;

    private NewGameButton newGameButton;

    private Star[] stars;

    public GameOverScreen(Game game){
        super();
        this.game = game;
    }

    @Override
    public void show() {
        super.show();
        atlas = new TextureAtlas("textures/mainAtlas.tpack");
        bg = new Texture("textures/bg.jpg");
        background = new Background(bg);
        font = new Font("font/font.fnt", "font/font.png");
        font.setSize(FONT_SIZE);

        newGameButton = new NewGameButton(atlas, game);

        stars = new Star[STAR_COUNT];
        for (int i = 0; i < STAR_COUNT; i++){
            stars[i] = new Star(atlas);
        }
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        draw();
    }

    @Override
    public void resize(Rect worldBounds) {
        background.resize(worldBounds);
        newGameButton.resize(worldBounds);
        for (Star star : stars){
            star.resize(worldBounds);
        }
    }

    @Override
    public void dispose() {
        atlas.dispose();
        bg.dispose();
        super.dispose();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        newGameButton.touchDown(touch, pointer,button);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        newGameButton.touchUp(touch, pointer, button);
        return false;
    }

    private void update(float delta){
        newGameButton.update(delta);
        for(Star star : stars){
            star.update(delta);
        }
    }

    private void draw(){
        batch.begin();
        background.draw(batch);
        for (Star star : stars){
            star.draw(batch);
        }
        newGameButton.draw(batch);
        printInfo();
        batch.end();
    }

    private void printInfo() {
        font.draw(batch, "Statistics: ", worldBounds.pos.x, worldBounds.getTop() - MARGIN, Align.center);
    }
}
