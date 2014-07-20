package edicion;

import org.uqbar.arena.actions.MessageSend;
import org.uqbar.arena.aop.windows.TransactionalDialog;
import org.uqbar.arena.layout.ColumnLayout;
import org.uqbar.arena.layout.VerticalLayout;
import org.uqbar.arena.widgets.Button;
import org.uqbar.arena.widgets.Label;
import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.widgets.Selector;
import org.uqbar.arena.widgets.TextBox;
import org.uqbar.arena.windows.WindowOwner;

import dulces.Dulce;

import appModel.AdministradorDeNiveles;

public class ObjetivoWindow extends TransactionalDialog<AdministradorDeNiveles>{

	public ObjetivoWindow(WindowOwner owner, AdministradorDeNiveles model) {
		super(owner, model);
	}
	
	@Override
	protected void createFormPanel(Panel mainPanel) {
		this.setTitle("Objetivo");
		this.setTaskDescription("Edita objetivo");
		
		Panel form = new Panel(mainPanel);
		form.setLayout(new ColumnLayout(2));

		new Label(form).setText("Cantidad");
		new TextBox(form)
			.setWidth(24)
			.bindValueToProperty("cantidad");

		this.crearSelectorDeColores(form);
	}

	private void crearSelectorDeColores(Panel form) {
		Panel panelColores = new Panel(form);
		panelColores.setLayout(new VerticalLayout());

		new Label(panelColores)
			.setText("Selecciona un color");

		Selector<Dulce> dificultades = new Selector<Dulce>(panelColores);
		dificultades.allowNull(false);
		dificultades.bindValueToProperty("color");
		dificultades.setContents(Dulce.getDulces(), "colores");
	}

	@Override
	protected void addActions(Panel actions) {
		new Button(actions)
			.setCaption("Aceptar")
			.onClick(new MessageSend(this, "accept"))
			.setAsDefault()
			.disableOnError()
			.bindEnabledToProperty("objetivoValido");
	}

}
