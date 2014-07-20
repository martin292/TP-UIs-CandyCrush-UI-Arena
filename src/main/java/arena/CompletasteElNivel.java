package arena;

import nivel.Nivel;

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

import appModel.FinDeNivel;

public class CompletasteElNivel extends SimpleWindow<FinDeNivel> {

	public CompletasteElNivel(WindowOwner parent, FinDeNivel model) {
		super(parent, model);
	}

	@Override
	protected void addActions(Panel actionsPanel) {
		
		new Button(actionsPanel)
		.setCaption("ir a siguiente nivel")
		.onClick(new MessageSend(this, "pasarSiguienteNivel"));
		
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
		new Label(form).bindValueToProperty("nombreNivel");
		
		new Label(form).setText("puntos obtenidos: ");
		new Label(form).bindValueToProperty("puntosObtenidos");
		
		new Label(form).setText("puntos totales: ");
		new Label(form).bindValueToProperty("puntosTotales");
		
		new Label(form).setText("tu próximo desafio es: ");
		new Label(form).bindValueToProperty("nd.nivel.siguienteNivel.nombre");
		
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
		Table<Nivel> table = new Table<Nivel>(form);
		table.setHeigth(100);
		table.setWidth(300);
		
		table.bindItemsToProperty("listaDeNiveles");
		//this.describeResultsGrid(table);
	}
	
	protected void describeResultsGrid(Table<Nivel> table){
		new Column<Nivel>(table)
		.setTitle("Nivel")
		.setFixedSize(150)
		.bindContentsToProperty("nombreNivel");
	
		new Column<Nivel>(table)
		.setTitle("Estado")
		.setFixedSize(150)
		.bindContentsToProperty("estadoNivel");
	}
}