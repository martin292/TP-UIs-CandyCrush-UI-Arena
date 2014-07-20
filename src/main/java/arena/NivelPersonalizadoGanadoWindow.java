package arena;

import org.uqbar.arena.actions.MessageSend;
import org.uqbar.arena.widgets.Button;
import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.windows.WindowOwner;

import partida.Partida;

public class NivelPersonalizadoGanadoWindow extends NivelGanadoWindow {

	public NivelPersonalizadoGanadoWindow(WindowOwner parent, Partida partida) {
		super(parent, partida);
	}
	
	@Override
	protected void addActions(Panel actionsPanel) {
		new Button(actionsPanel)
			.setCaption("Volver")
			.onClick(new MessageSend(this, "close"));
	}
}
