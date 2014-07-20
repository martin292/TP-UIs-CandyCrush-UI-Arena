package arena;

import mundo.Jugador;

import org.uqbar.arena.actions.MessageSend;
import org.uqbar.arena.widgets.Button;
import org.uqbar.arena.widgets.Label;
import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.windows.SimpleWindow;
import org.uqbar.arena.windows.WindowOwner;

public class JuegoTerminadoWindow extends SimpleWindow<Jugador>{

	public JuegoTerminadoWindow(WindowOwner parent, Jugador model) {
		super(parent, model);
	}
	
	@Override
	protected void createMainTemplate(Panel mainPanel) {
		this.setTitle("Felicitaciones");
		this.setTaskDescription("Terminaste!");

		super.createMainTemplate(mainPanel);
		
	}
	
	@Override
	protected void addActions(Panel actionsPanel) {		
		new Button(actionsPanel)
			.setCaption("Reiniciar Juego")
			.onClick(new MessageSend(this, "reiniciarJuego"));
		
		new Button(actionsPanel)
			.setCaption("Volver al mundo")
			.onClick(new MessageSend(this, "close"));
	
		
	}
		
	public void reiniciarJuego(){
		this.close();
		this.getModelObject().reiniciarJuego();
	}

	@Override
	protected void createFormPanel(Panel mainPanel) {
		new Label(mainPanel).setText("Selecciona que hacer de tu vida");
	}

}
