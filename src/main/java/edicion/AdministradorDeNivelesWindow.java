package edicion;

import objetivos.*;

import org.uqbar.arena.windows.Dialog;
import org.uqbar.arena.actions.MessageSend;
import org.uqbar.arena.aop.windows.TransactionalDialog;
import org.uqbar.arena.bindings.NotNullObservable;
import org.uqbar.arena.layout.ColumnLayout;
import org.uqbar.arena.layout.HorizontalLayout;
import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.widgets.TextBox;
import org.uqbar.arena.windows.WindowOwner;
import org.uqbar.arena.widgets.Label;
import org.uqbar.arena.widgets.tables.Column;
import org.uqbar.arena.widgets.tables.Table;
import org.uqbar.arena.widgets.Button;
import org.uqbar.commons.model.ObservableUtils;

import appModel.AdministradorDeNiveles;

public class AdministradorDeNivelesWindow extends TransactionalDialog<AdministradorDeNiveles>{


	public AdministradorDeNivelesWindow(WindowOwner owner,
			AdministradorDeNiveles model) {
		super(owner, model);
	}

	@Override
	protected void createFormPanel(Panel mainPanel) {
		Panel form = new Panel(mainPanel);
		form.setLayout(new ColumnLayout(2));
		
		final int LARGO = 24;

		this.setTitle("Editor de niveles");
		this.setTaskDescription("Edite el nivel:");

		new Label(form).setText("Nombre");
		new TextBox(form).setWidth(200).bindValueToProperty("nombre");

		new Label(form).setText("Filas");
		new TextBox(form).setWidth(LARGO).bindValueToProperty("filas");

		new Label(form).setText("Columnas");
		new TextBox(form).setWidth(LARGO).bindValueToProperty("columnas");

		new Label(form).setText("Cant mov");
		new TextBox(form).setWidth(LARGO).bindValueToProperty("cantMovimientos");

		this.crearTablaDeObjetivos(mainPanel);
		this.crearBotonesDeLaTabla(mainPanel);
	}

	/**
	 * Crea una tabla de Objetivos.
	 * 
	 * Bindea los items de esa tabla con la propiedad "objetivos" y el valor seleccionado 
	 * con la propiedad "objSeleccionado" del appModel.
	 * 
	 * Crea una columna.
	 * 
	 * @param mainPanel
	 */
	protected void crearTablaDeObjetivos(Panel mainPanel){

		 Table<Objetivo> table = new Table<Objetivo>(mainPanel, Objetivo.class);
		 table.setHeigth(150);
		 table.setWidth(300);

		 table.bindItemsToProperty("objetivos");
		 table.bindValueToProperty("objSeleccionado");

		 this.crearResultadosDeLaTabla(table);

	}

	/**
	 * Crea una columna d Objetivos con el titulo "Objetivo: " y bindea el contenido de la columna con la propiedad " " de la clase Objetivo
	 * @param table
	 */
	protected void crearResultadosDeLaTabla(Table<Objetivo> table){		
		 new Column<Objetivo>(table)
		     .setTitle("Objetivo: ")
		      .bindContentsToProperty("nombre");	 
	}

	/**
	 * Crea un panel con 4 botones para manejar la tabla
	 * @param mainPanel
	 */
	protected void crearBotonesDeLaTabla(Panel mainPanel){
		Panel actionsPanel = new Panel(mainPanel);
		actionsPanel.setLayout(new HorizontalLayout());


		new Button(actionsPanel)
			.setCaption("Agregar E.P.C")
			.onClick(new MessageSend(this, "agregarExplosionesPorColor"));

		new Button(actionsPanel)
			.setCaption("Agregar G.E")
			.onClick(new MessageSend(this, "agregarGrandesExplosiones"));

		Button editar = new Button(actionsPanel)
			.setCaption("Editar")
			.onClick(new MessageSend(this, "editarObjetivo"));

		Button borrar = new Button(actionsPanel)
			.setCaption("Borrar")
			.onClick(new MessageSend(this.getModelObject(), "borrarObjetivo"));		

		NotNullObservable elementSelected = new NotNullObservable("objSeleccionado");
		editar.bindEnabled(elementSelected);
		borrar.bindEnabled(elementSelected);

	}

	public void agregarObjetivo(Objetivo objetivo){		
		this.getModelObject().seleccionarNuevoObjetivo(objetivo);
		this.openDialog(new ObjetivoWindow(this, this.getModelObject()));

		ObservableUtils.firePropertyChanged(
				this.getModelObject(), "objetivos", this.getModelObject().getObjetivos());
	}
	
	/**
	 * Crea un objetivo ExplosionesPorColor, lo selecciona y abre una ventana de edicion de objetivos
	 */
	public void agregarExplosionesPorColor(){	
		this.agregarObjetivo(new ExplosionesPorColor());
	}

	/**
	 * Crea un objetivo GrandesExplosiones, lo selecciona y abre una ventana de edicion de objetivos
	 */
	public void agregarGrandesExplosiones(){
		this.agregarObjetivo(new GrandesExplosiones());
	}

	/**
	 * Edita un objetivo
	 */
	public void editarObjetivo(){
		this.getModelObject().editarObjetivo();
		this.openDialog(new ObjetivoWindow(this, this.getModelObject()));
	}

	protected void openDialog(Dialog<?> dialog) {
		dialog.onAccept(new MessageSend(this.getModelObject(), "actualizarObjetivos"));
		dialog.open();
	}

	@Override
	protected void addActions(Panel actions) {
		new Button(actions)
			.setCaption("Aceptar")
			.onClick(new MessageSend(this, "accept"))
			.setAsDefault()
			.disableOnError()
			.bindEnabledToProperty("nivelValido");
	}

}