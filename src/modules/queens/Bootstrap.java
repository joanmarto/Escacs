package modules.queens;

import modules.queens.controller.MainWindowEvents;
import modules.queens.model.BoardDefinition;
import modules.queens.view.MainWindow;

/**
 *
 * @author Bernat Galmés Rubert
 */
public class Bootstrap implements Runnable {
    @Override
    public void run() {
        BoardDefinition model = new BoardDefinition();
        MainWindow window = new MainWindow(model);
        MainWindowEvents eventHandler = new MainWindowEvents(window, model);
        
        window.attachActionLister(eventHandler);
        window.attachChangeListener(eventHandler);
        window.setVisible(true);
    }
}
