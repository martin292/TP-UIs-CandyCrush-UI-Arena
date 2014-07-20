package edicion;

import org.uqbar.arena.Application;
import org.uqbar.arena.windows.Window;


public class PruebaEditorApp extends Application{

	public static void main(String[] args) {
		new PruebaEditorApp().start();
	}
	
	@Override
	protected Window<?> createMainWindow() {
		return new NivelesCreadosWindow(this);
	}

}
