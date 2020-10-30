package ru.mygame;

import com.badlogic.gdx.Game;

import ru.mygame.screen.MenuScreen;

public class StarGame extends Game{

	@Override
	public void create() {
		setScreen(new MenuScreen(this));
	}

}
