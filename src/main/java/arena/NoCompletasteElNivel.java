package arena;

import mundo.Jugador;
import mundo.Partida;
import nivel.Nivel;
import nivel.NivelParaJugar;

import objetivos.ObjetivoParaCumplir;

import org.uqbar.arena.actions.MessageSend;
import org.uqbar.arena.layout.ColumnLayout;
import org.uqbar.arena.widgets.Button;
import org.uqbar.arena.widgets.Label;
import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.widgets.tables.Column;
import org.uqbar.arena.widgets.tables.Table;
import org.uqbar.arena.windows.SimpleWindow;
import org.uqbar.arena.windows.WindowOwner;

public class NoCompletasteElNivel extends SimpleWindow<Partida> {

	public NoCompletasteElNivel(WindowOwner parent, Partida partida) {
		super(parent, partida);
	}
	
	@Override
	protected void createMainTemplate(Panel mainPanel) {
		this.setTitle("Perdiste!");
		this.setTaskDescription("Mala suerte!");

		super.createMainTemplate(mainPanel);
		
	}
	
	@Override
	protected void createFormPanel(Panel mainPanel) {
		Panel form = new Panel(mainPanel);
		form.setLayout(new ColumnLayout(0));
		new Label(form).setText("No completaste el nivel").setFontSize(30);
		
		this.llenarParteDeIzq(form);
		this.llenarParteDerecha(form);
		
	}
	
	@Override
	protected void addActions(Panel actionsPanel) {
		
		new Button(actionsPanel)
		.setCaption("Intentar nuevamente");
		//.onClick(new MessageSend(this, "jugarNivelNuevamente"));
		
		new Button(actionsPanel)
		.setCaption("Volver al mundo");
		//.onClick(new MessageSend(this, "volver"));
		
	}
	
	protected void llenarParteDeIzq(Panel form){
		new Label(form).setText("no cumpliste con todos los objetivos");
		
		form.setLayout(new ColumnLayout(1));
		new Label(form).setText("puntos obtenidos: ");
		new Label(form).bindValueToProperty("puntosAlcanzados");
	}
	
	protected void llenarParteDerecha(Panel form){
		Panel jugadorPanel = new Panel(
				form, this.getModelObject().getNivelParaJugar().getJugador());
		Table<NivelParaJugar> table = 
				new Table<NivelParaJugar>(jugadorPanel, NivelParaJugar.class);
		table.setHeigth(100);
		table.setWidth(300);
		
		table.bindItemsToProperty("nivelesParaJugar");
		
		this.describeResultsGrid(table);
	}
	
	protected void describeResultsGrid(Table<NivelParaJugar> table){
		new Column<NivelParaJugar>(table)
		.setTitle("Nivel")
		.setFixedSize(150)
		.bindContentsToProperty("nivel");
		
		new Column<NivelParaJugar>(table)
		.setTitle("Estado")
		.setFixedSize(150)
		.bindContentsToProperty("completado");
	}
}