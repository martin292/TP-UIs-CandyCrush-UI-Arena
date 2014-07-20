package edicion;

import mundo.Jugador;
import mundo.NivelParaJugar;
import dificultad.Dificultad;

import org.uqbar.arena.actions.MessageSend;
import org.uqbar.arena.aop.windows.TransactionalDialog;
import org.uqbar.arena.layout.VerticalLayout;
import org.uqbar.arena.widgets.Button;
import org.uqbar.arena.widgets.Label;
import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.widgets.Selector;
import org.uqbar.arena.windows.WindowOwner;

import appModel.AdministradorDeNiveles;
import arena.PartidaWindow;

public class DificultadWindow extends TransactionalDialog<AdministradorDeNiveles>{

	public DificultadWindow(WindowOwner owner, AdministradorDeNiveles model) {
		super(owner, model);
	}
	
	@Override
	protected void createFormPanel(Panel mainPanel) {
		this.setTitle("Seleccionar dificultad");
		this.setTaskDescription("Selecciona dificultad");
		
		Panel form = new Panel(mainPanel);
		form.setLayout(new VerticalLayout());

		this.crearSelectorDeDificultades(form);

		Button botonJugar = new Button(form);
		botonJugar.setCaption("JUGAR");
		botonJugar.onClick(new MessageSend(this, "jugar"));
	}
	
	private void crearSelectorDeDificultades(Panel form) {
		Panel dificultadesPanel = new Panel(form);
		dificultadesPanel.setLayout(new VerticalLayout());

		new Label(dificultadesPanel)
			.setText("Selecciona dificultad");

		Selector<Dificultad> dificultades = new Selector<Dificultad>(dificultadesPanel);
		dificultades.allowNull(false);
		dificultades.bindValueToProperty("dificultad");
		dificultades.setContents(Dificultad.getDificultades(), "dificultades");		
	}

	@Override
	protected void addActions(Panel actions) {		
		new Button(actions) //
			.setCaption(" Cancelar ")
			.onClick(new MessageSend(this, "cancel"));
	}

	public void jugar(){
		Jugador jugador = new Jugador("Usuario");
		NivelParaJugar nivel = new NivelParaJugar(
				jugador, this.getModelObject().getNivelSeleccionado(), true);

		this.close();
		new PartidaWindow(this, nivel, this.getModelObject().getDificultad()).open();
	}

}