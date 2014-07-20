package arena;

import mundo.Nivel;
import mundo.NivelParaJugar;

import objetivos.Objetivo;

import org.uqbar.arena.actions.MessageSend;
import org.uqbar.arena.layout.ColumnLayout;
import org.uqbar.arena.widgets.Button;
import org.uqbar.arena.widgets.Label;
import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.widgets.tables.Column;
import org.uqbar.arena.widgets.tables.Table;
import org.uqbar.arena.windows.SimpleWindow;
import org.uqbar.arena.windows.WindowOwner;

import partida.Partida;

public class NivelGanadoWindow extends SimpleWindow<Partida> {

	public NivelGanadoWindow(WindowOwner parent, Partida partida) {
		super(parent, partida);
	}

	@Override
	protected void addActions(Panel actionsPanel) {
		
		new Button(actionsPanel)
		.setCaption("volver al mundo")
		.onClick(new MessageSend(this, "close"));
		
		new Button(actionsPanel)
		.setCaption("ir a siguiente nivel")
		.onClick(new MessageSend(this, "pasarAlSiguienteNivel"));
		
	}
	
	public void pasarAlSiguienteNivel(){
		NivelParaJugar siguienteNivel = this.getModelObject().getNivelParaJugar().siguienteNivel();
		this.close();
		new PartidaWindow(this, siguienteNivel, 
				this.getModelObject().getTablero().getDificultad()).open();
	}
	
	@Override
	protected void createFormPanel(Panel mainPanel) {
		Panel form = new Panel(mainPanel);
		new Label(form).setText("En hora buena!").setFontSize(30);
		form.setLayout(new ColumnLayout(2));
		
		this.llenarParteDeIzq(form);
		this.llenarParteDerecha(form);
	}
	
	protected void llenarParteDeIzq(Panel form){
		form.setLayout(new ColumnLayout(2));
		new Label(form).setText("completaste el nivel: ");
		new Label(form).bindValueToProperty("nivelParaJugar");
		
		new Label(form).setText("puntos obtenidos: ");
		new Label(form).bindValueToProperty("puntosAlcanzados");
		
//		new Label(form).setText("puntos totales: ");
//		new Label(form).bindValueToProperty("puntosTotales");
		
//		new Label(form).setText("tu próximo desafio es: ");
//		new Label(form).bindValueToProperty("nd.nivel.siguienteNivel.nombre");
		
		crearTablaConObjetivos(form);
	}
	
	protected void crearTablaConObjetivos(Panel form){
		form.setLayout(new ColumnLayout(1));
		Table<Objetivo> table = new Table<Objetivo>(form);
		table.setHeigth(100);
		table.setWidth(300);
		table.bindItemsToProperty("objetivos");
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
		.setTitle("Completado")
		.setFixedSize(150)
		.bindContentsToTransformer(new BooleanToSiNoTransformer());
	}
}