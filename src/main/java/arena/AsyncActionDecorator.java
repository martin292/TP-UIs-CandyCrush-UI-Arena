package arena;

import org.uqbar.lacar.ui.model.Action;

/**
 * Decorates a given {@link Action} in order to execute it
 * in a different thread (asynchronously).
 * This is useful to avoid freezing the ui thread.
 * 
 * @author jfernandes
 */
public class AsyncActionDecorator implements Action {
	private Action decoratee;

	public AsyncActionDecorator(Action decoratee) {
		this.decoratee = decoratee;
	}

	public void execute() {
		new Thread(new Runnable() {
			public void run() {
				decoratee.execute();
			}
		}).start();
	}

}