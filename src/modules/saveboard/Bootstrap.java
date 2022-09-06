package modules.saveboard;

import modules.saveboard.controller.MainWindowEvents;
import modules.saveboard.model.BoardDefinition;
import modules.saveboard.view.MainWindow;

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
