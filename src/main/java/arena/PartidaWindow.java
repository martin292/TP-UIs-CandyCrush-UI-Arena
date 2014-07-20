package arena;

import objetivos.ObjetivoParaCumplir;
import mundo.JuegoTerminadoException;
import mundo.NivelParaJugar;
import dificultad.Dificultad;

import org.eclipse.swt.widgets.Display;
import org.uqbar.arena.actions.MessageSend;
import org.uqbar.arena.layout.ColumnLayout;
import org.uqbar.arena.widgets.Button;
import org.uqbar.arena.widgets.Label;
import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.widgets.tables.Column;
import org.uqbar.arena.widgets.tables.Table;
import org.uqbar.arena.windows.SimpleWindow;
import org.uqbar.arena.windows.WindowOwner;
import org.uqbar.lacar.ui.model.Action;

import partida.Celda;
import partida.Partida;
import partida.PartidaGanadaException;
import partida.PartidaPerdidaException;
import partida.PartidaPersonalizadaGanadaException;

public class PartidaWindow extends SimpleWindow<Partida>  {
	
	public PartidaWindow(WindowOwner parent, 
			NivelParaJugar nivelParaJugar, Dificultad dificultad) {
		super(parent, new Partida(nivelParaJugar, dificultad));
	}
	
	@Override
	protected void createMainTemplate(Panel mainPanel) {
		this.setTitle("Partida En Juego");
		this.setTaskDescription("A jugar!");

		super.createMainTemplate(mainPanel);

		Panel panel = new Panel(mainPanel)
			.setLayout(new ColumnLayout(2));
		
		this.createCellActions(panel);
		
		Panel ObjetivesAndLogPanel = new Panel(panel);
		this.createObjetivesGrid(ObjetivesAndLogPanel);
		this.createLog(ObjetivesAndLogPanel);
		
		this.createGameActions(mainPanel);
		
	}

	@Override
	protected void createFormPanel(Panel mainPanel) {
		Panel datosJuego = new Panel(mainPanel)
			.setLayout(new ColumnLayout(2));
		
		this.createEstadisticas(datosJuego);
		this.crearDatosPartida(datosJuego);
		
	}
	
	protected void createEstadisticas(Panel panel){
		Panel estadisticas = new Panel(panel)
			.setLayout(new ColumnLayout(2));
		
		final int LARGO = 66;

		new Label(estadisticas).setText("puntos");
		new Label(estadisticas)
			.setWidth(LARGO)
			.bindValueToProperty("puntosAlcanzados");
		
		new Label(estadisticas).setText("movimientos");
		new Label(estadisticas)
			.setWidth(LARGO)
			.bindValueToProperty("movimientosRestantes");
	}
	
	protected void crearDatosPartida(Panel panel){
		Panel datosPanel = new Panel(panel)
			.setLayout(new ColumnLayout(2));
		
		Panel filaColumna = new Panel(datosPanel)
			.setLayout(new ColumnLayout(2));
		new Label(filaColumna).setText("fila");
		new Label(filaColumna).bindValueToProperty("fila");

		new Label(filaColumna).setText("columna");//.setForeground(Color.BLUE);
		new Label(filaColumna).bindValueToProperty("columna");
		
		new Label(datosPanel).bindValueToProperty("representacionVisual");
	}
	
	@Override
	protected void addActions(Panel mainPanel) {
		Panel ganarPerder = new Panel(mainPanel)
			.setLayout(new ColumnLayout(3));
	
		final int LARGO = 72;
		
	new Button(ganarPerder).setCaption("Terminar")
		.onClick(new MessageSend(this, "termine"))
		.setWidth(LARGO);
	
	new Button(ganarPerder).setCaption("Ganar")
		.onClick(new MessageSend(this, "gane"))
		.setWidth(LARGO);
	
	new Button(ganarPerder).setCaption("Perder")
		.onClick(new MessageSend(this, "perdi"))
		.setWidth(LARGO);
	}
	
	protected void createCellActions(Panel mainPanel) {
		Partida partida = this.getModelObject();
		int filas = partida.filasTablero();
		int columnas = partida.columnasTablero();
		
		Panel celdasPanel = new Panel(mainPanel)
			.setLayout(new ColumnLayout(columnas));
		
		for(int fila = filas-1; fila >= 0; fila--)
		{	for(int columna = 0; columna < columnas; columna++)
			{	this.crearCeldaView(celdasPanel, columna, fila, 
					partida.getTablero().getCelda(columna, fila));
			}
		}
	}
	
	protected void crearCeldaView(Panel container, int columna, int fila, Celda celda){
		Panel panelDeCelda = new Panel(container, celda);
	    new Button(panelDeCelda)
	    	.onClick(new AsyncActionDecorator(
	    			this.createAction(columna, fila)))
	    	.bindCaptionToProperty("dulce");
	}

	protected Action createAction(final int columna,final int fila){
		return new Action()
		{	public void execute()
			{	try {getModelObject().elegirCelda(columna, fila);}
					catch (PartidaPersonalizadaGanadaException e)
						{asyncGanarPersonalizada();}		
					catch (JuegoTerminadoException e) 
						{asyncTerminar();}
					catch (PartidaGanadaException e) 
						{asyncGanar();}
					catch (PartidaPerdidaException e) 
						{asyncPerder();}
			}
		};
	}
	
	public void asyncTerminar(){
		Display.getDefault().asyncExec(new Runnable() {
    		public void run() {
                terminar();
    		}
		});
	}
	
	public void asyncGanar(){
		Display.getDefault().asyncExec(new Runnable() {
    		public void run() {
                ganar();
    		}
		});
	}
	
	public void asyncGanarPersonalizada(){
		Display.getDefault().asyncExec(new Runnable() {
    		public void run() {
                ganarPersonalizada();
    		}
		});
	}
	
	public void asyncPerder(){
		Display.getDefault().asyncExec(new Runnable() {
    		public void run() {
                perder();
    		}
		});
	}
	
	protected void createObjetivesGrid(Panel mainPanel) {
		Panel panel = new Panel(mainPanel);
		Table<ObjetivoParaCumplir> table = 
				new Table<ObjetivoParaCumplir>(panel, ObjetivoParaCumplir.class);
		
		table.bindItemsToProperty("objetivos");

		this.describeObjetiveGrid(table);
		
	}
	
	protected void describeObjetiveGrid(Table<ObjetivoParaCumplir> table) {
		new Column<ObjetivoParaCumplir>(table)
			.setTitle("Objetivo")
			.setFixedSize(210)
			.bindContentsToProperty("objetivoACumplir");

		new Column<ObjetivoParaCumplir>(table)
			.setTitle("Estado")
			.setFixedSize(90)
			.bindContentsToProperty("estado");
	}
	
	protected void createLog(Panel mainPanel){
		Panel log = new Panel(mainPanel);
		new Label(log)
			.setWidth(300)
			.setHeigth(100)
			.bindValueToProperty("logMovimiento");
	}
	
	protected void createGameActions(Panel mainPanel) {
		Panel gamePanel = new Panel(mainPanel)
			.setLayout(new ColumnLayout(2));
		
		new Button(gamePanel)
			.setCaption("Reiniciar")
			.onClick(new MessageSend(this.getModelObject(), "reiniciar"))
			.setWidth(100);
		
		new Button(gamePanel)
			.setCaption("Salir")
			.onClick(new MessageSend(this, "close"))
			.setWidth(100);
	}
	
	public void perder(){
		close();
		new NivelPerdidoWindow(this, this.getModelObject()).open();
	}
	
	public void ganar(){
		close();
		new NivelGanadoWindow(this, this.getModelObject()).open();
	}
	
	public void ganarPersonalizada(){
		close();
		new NivelPersonalizadoGanadoWindow(this, this.getModelObject()).open();
	}
	
	
	public void terminar(){
		close();
		new NivelGanadoYJuegoTerminadoWindow(this, this.getModelObject()).open();
	}
	
	public void perdi(){
		close();
		try{this.getModelObject().getNivelParaJugar().perder();}
			catch (PartidaPerdidaException e)
				{new NivelPerdidoWindow(this, this.getModelObject()).open();}
	}
	
	public void gane(){
		Partida partida = this.getModelObject();
		close();
		try{partida.getNivelParaJugar().ganar();}
			catch (PartidaPersonalizadaGanadaException e)
				{new NivelPersonalizadoGanadoWindow(this, this.getModelObject()).open();}	
			catch (JuegoTerminadoException e)
				{new NivelGanadoWindow(this, partida).open();
				new JuegoTerminadoWindow(this, partida.getNivelParaJugar().getJugador()).open();}	
			catch (PartidaGanadaException e)
				{new NivelGanadoWindow(this, partida).open();}
	}
	
	public void termine(){
		Partida partida = this.getModelObject();
		close();
		try{partida.terminar();}
			catch (PartidaPersonalizadaGanadaException e)
				{new NivelPersonalizadoGanadoWindow(this, this.getModelObject()).open();}	
			catch (JuegoTerminadoException e)
				{new NivelGanadoYJuegoTerminadoWindow(this, partida).open();}
			catch (PartidaGanadaException e)
				{new NivelGanadoWindow(this, partida).open();}
			catch (PartidaPerdidaException e)
				{new NivelPerdidoWindow(this, partida).open();}
	}
	
}
