package edicion;

import mundo.Nivel;

import org.uqbar.arena.widgets.Button;
import org.uqbar.arena.windows.Dialog;
import org.uqbar.arena.windows.SimpleWindow;
import org.uqbar.arena.actions.MessageSend;
import org.uqbar.arena.bindings.NotNullObservable;
import org.uqbar.arena.layout.HorizontalLayout;
import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.windows.WindowOwner;
import org.uqbar.arena.widgets.tables.Column;
import org.uqbar.arena.widgets.tables.Table;
import org.uqbar.commons.model.ObservableUtils;

import edicion.AdministradorDeNivelesWindow;
import appModel.AdministradorDeNiveles;

public class NivelesCreadosWindow extends SimpleWindow<AdministradorDeNiveles>{

	public NivelesCreadosWindow(WindowOwner owner) {
		super(owner, new AdministradorDeNiveles());
		this.getModelObject().actualizar();
	}
	
	@Override
	protected void createMainTemplate(Panel mainPanel) {
		this.setTitle("Niveles Creados");
		this.setTaskDescription("Crea o edita un nivel");
		
		super.createMainTemplate(mainPanel);

		this.crearTablaDeNiveles(mainPanel);
		this.crearBotonesDeLaTabla(mainPanel);
		this.addEditorActions(mainPanel);
	}

	@Override
	protected void createFormPanel(Panel mainPanel) {
	}

	/**
	 * Crea una tabla de Niveles.
	 * 
	 * Bindea los items de esa tabla con la propiedad "niveles" y el valor seleccionado 
	 * con la propiedad "nivelSeleccionado" del appModel.
	 * 
	 * Crea una columna.
	 * 
	 * @param mainPanel
	 */
	protected void crearTablaDeNiveles(Panel mainPanel) {
		Panel tablaPanel = new Panel(mainPanel);
		
		Table<Nivel> table = new Table<Nivel>(tablaPanel, Nivel.class);
		table.setHeigth(200);
		table.setWidth(300);

		table.bindItemsToProperty("niveles");
		table.bindValueToProperty("nivelSeleccionado");

		this.crearResultadosDeLaTabla(table);
	}

	/**
	 * Crea una columna de niveles con el titulo "Nivel:" y bindea el 
	 * contenido de la columna con la propiedad "nombre"
	 *  de la clase Nivel
	 * @param table
	 */
	protected void crearResultadosDeLaTabla(Table<Nivel> table){
		new Column<Nivel>(table)
		    .setTitle("Nivel")
		    .bindContentsToProperty("nombre");
	}

	/**
	 * Crea un panel con 4 botones para manejar la tabla
	 * @param mainPanel
	 */
	protected void crearBotonesDeLaTabla(Panel mainPanel) {
		Panel actionsPanel = new Panel(mainPanel);
		actionsPanel.setLayout(new HorizontalLayout());

		Button agregar = new Button(actionsPanel);
		agregar.setCaption("Agregar");
		agregar.onClick(new MessageSend(this, "agregarNivel"));

		Button editar = new Button(actionsPanel);
		editar.setCaption("Editar");
		editar.onClick(new MessageSend(this, "editarNivel"));

		Button borrar = new Button(actionsPanel);
		borrar.setCaption("Borrar");
		borrar.onClick(new MessageSend(this.getModelObject(), "borrarNivel"));

		Button jugar = new Button(actionsPanel);
		jugar.setCaption("Jugar");
		jugar.onClick(new MessageSend(this, "jugarNivel"));

		NotNullObservable elementSelected = new NotNullObservable("nivelSeleccionado");
		editar.bindEnabled(elementSelected);
		borrar.bindEnabled(elementSelected);
		jugar.bindEnabled(elementSelected);
	}
	
	public void abrirEditorDeNiveles(){
		this.getModelObject().actualizar();
		this.openDialog(new AdministradorDeNivelesWindow(this, this.getModelObject()));
		ObservableUtils.firePropertyChanged(this.getModelObject(), "niveles", this.getModelObject().getNiveles());
	}

	/**
	 * Crea un nuevo nivel
	 * actualiza la lista de niveles del appModel
	 * abre una ventana de edicion de niveles
	 */
	public void agregarNivel(){
		this.getModelObject().agregarNivel();
		this.abrirEditorDeNiveles();
	}

	/**
	 * abre la ventana del editor de niveles
	 */
	public void editarNivel(){
		this.getModelObject().editarNivel();
		this.abrirEditorDeNiveles();
	}

	/**
	 * abre la ventana de seleccion de dificultad
	 */
	public void jugarNivel(){
		new DificultadWindow(this, this.getModelObject()).open();
	}

	protected void openDialog(Dialog<?> dialog) {
		dialog.onAccept(new MessageSend(this.getModelObject(), "actualizar"));
		dialog.open();
	}

	public void salir(){
		this.close();
	}

	protected void addEditorActions(Panel mainPanel){
		Panel accionesEditor = new Panel(mainPanel);
		new Button(accionesEditor)
		.setCaption(" Salir ")
		.onClick(new MessageSend(this, "salir"));
	}
	
	@Override
	protected void addActions(Panel actionsPanel) {

	}

}