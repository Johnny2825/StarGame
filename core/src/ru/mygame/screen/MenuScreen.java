package ru.mygame.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.mygame.base.BaseScreen;
import ru.mygame.math.Rect;
import ru.mygame.sprite.Background;
import ru.mygame.sprite.ExitButton;
import ru.mygame.sprite.PlayButton;
import ru.mygame.sprite.Star;


public class MenuScreen extends BaseScreen {

    private static final int STAR_COUNT = 256;

    private Game game;

    private Texture bg;
    private Background background;
    private TextureAtlas atlas;
    private ExitButton exitButton;
    private PlayButton playButton;

    private Star[] stars;

    public MenuScreen(Game game){
        super();
        this.game = game;
    }


    @Override
    public void show() {
        super.show();
        atlas = new TextureAtlas("textures/menuAtlas.tpack");
        bg = new Texture("textures/bg.jpg");
        background = new Background(new TextureRegion(bg));
        exitButton = new ExitButton(atlas);
        playButton = new PlayButton(atlas, game);

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
        for (Star star : stars){
            star.resize(worldBounds);
        }
        exitButton.resize(worldBounds);
        playButton.resize(worldBounds);
    }

    @Override
    public void dispose() {
        atlas.dispose();
        bg.dispose();
        super.dispose();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        exitButton.touchDown(touch, pointer,button);
        playButton.touchDown(touch, pointer,button);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        exitButton.touchUp(touch, pointer, button);
        playButton.touchUp(touch, pointer, button);
        return false;
    }

    private void update(float delta){
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
        exitButton.draw(batch);
        playButton.draw(batch);
        batch.end();
    }

}
