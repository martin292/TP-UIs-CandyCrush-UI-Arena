package arena;


import org.uqbar.arena.actions.MessageSend;
import org.uqbar.arena.widgets.Button;
import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.windows.WindowOwner;

import partida.Partida;

public class NivelGanadoYJuegoTerminadoWindow extends NivelGanadoWindow {

	public NivelGanadoYJuegoTerminadoWindow(WindowOwner parent, Partida partida) {
		super(parent, partida);
	}
	
	@Override
	protected void addActions(Panel actionsPanel) {
		
		new Button(actionsPanel)
		.setCaption("Que paso?")
		.onClick(new MessageSend(this, "terminar"));
		
	}
	
	public void terminar(){
		this.close();
		new JuegoTerminadoWindow(this, 
				this.getModelObject().getNivelParaJugar().getJugador()).open();
	}

}
