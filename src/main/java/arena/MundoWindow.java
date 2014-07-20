package arena;

import mundo.*;
import dificultad.Dificultad;
import edicion.NivelesCreadosWindow;

import org.uqbar.arena.actions.MessageSend;
import org.uqbar.arena.layout.ColumnLayout;
import org.uqbar.arena.layout.VerticalLayout;
import org.uqbar.arena.widgets.Button;
import org.uqbar.arena.widgets.Label;
import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.widgets.Selector;
import org.uqbar.arena.windows.SimpleWindow;
import org.uqbar.arena.windows.WindowOwner;
import org.uqbar.lacar.ui.model.Action;

public class MundoWindow extends SimpleWindow<Jugador> {

	public MundoWindow(WindowOwner parent, Jugador model) {
		super(parent, model);
	}
	
	@Override
	protected void createMainTemplate(Panel mainPanel) {
		this.setTitle("Mundo");
		this.setTaskDescription("Selecciona hacia donde quieres ir");

		super.createMainTemplate(mainPanel);

		this.createLevelsActions(mainPanel);
		
		this.crearAccionesMundo(mainPanel);
	}

	@Override
	protected void createFormPanel(Panel mainPanel) {
		Panel datosJuego = new Panel(mainPanel)
			.setLayout(new ColumnLayout(2));
		
		this.crearEstadisticas(datosJuego);
		
		this.crearDificultades(datosJuego);

	}
	
	protected void crearEstadisticas(Panel panel){
		Panel estadisticas = new Panel(panel)
			.setLayout(new ColumnLayout(2));

		new Label(estadisticas).setText("Jugador: ");
		new Label(estadisticas)
			.bindValueToProperty("nombre");
		
		new Label(estadisticas).setText("puntaje total");
		new Label(estadisticas)
			.setWidth(74)
			.bindValueToProperty("puntaje");
	}
	
	protected void crearDificultades(Panel mainPanel){
		Panel dificultadesPanel = new Panel(mainPanel)
			.setLayout(new VerticalLayout());
		
		new Label(dificultadesPanel)
			.setText("Selecciona dificultad");
		
		new Selector<Dificultad>(dificultadesPanel)
			.setContents(Dificultad.getDificultades(), "dificultades disponibles")
			.allowNull(false)
			.bindValueToProperty("dificultadSeleccionada");
		
	}
	
	protected void createLevelsActions(Panel mainPanel){
		Panel nivelesPanel = new Panel(mainPanel)
			.setLayout(new ColumnLayout(4));
		
		for(NivelParaJugar nivel : this.getModelObject().getNivelesParaJugar())
		{	Panel nivelPanel = new Panel(nivelesPanel, nivel)
				.setLayout(new VerticalLayout());
			new Button(nivelPanel)
				.setCaption(nivel.toString())
				.onClick(this.jugarNivel(this, nivel))
				.bindEnabledToProperty("disponible");
			new Label(nivelPanel).setText("puntaje max");
			new Label(nivelPanel).bindValueToProperty("puntosObtenidos");
		}
	}
	
	protected Action jugarNivel(final WindowOwner parent, final NivelParaJugar nivelParaJugar){
		return new Action()
		{	public void execute()
			{	new PartidaWindow(parent, nivelParaJugar, 
					nivelParaJugar.getJugador().getDificultadSeleccionada()).open();
			}
		};
	}
	
	@Override
	protected void addActions(Panel actionsPanel) {
		new Button(actionsPanel)
			.setCaption("Editor")
			.onClick(new MessageSend(this, "editarNiveles"));
	
	}
	
	protected void crearAccionesMundo(Panel mainPanel){
		Panel accionesPanel = new Panel(mainPanel)
			.setLayout(new ColumnLayout(2));
		
		new Button(accionesPanel)
		.setCaption("Salir")
		.onClick(new MessageSend(this, "close"));
		
		new Button(accionesPanel)
		.setCaption("Reiniciar Juego")
		.onClick(new MessageSend(this.getModelObject(), "reiniciarJuego"));
	}
	
	public void editarNiveles(){
		new NivelesCreadosWindow(this).open();
	}

}
