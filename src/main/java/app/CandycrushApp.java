package app;

import org.uqbar.arena.Application;
import org.uqbar.arena.windows.Window;

import repos.MundoFactory;

import arena.MundoWindow;

public class CandycrushApp extends Application {

	public static void main(String[] args) {
		new CandycrushApp().start();
	}

	@Override
	protected Window<?> createMainWindow() {
		MundoFactory factory = new MundoFactory();
		factory.crearMundo();
		return new MundoWindow(this, factory.getJugadores().get(0));
	}

}
