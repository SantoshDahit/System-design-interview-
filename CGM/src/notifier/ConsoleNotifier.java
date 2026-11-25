package notifier;

import entity.Alert;

public class ConsoleNotifier implements Notifier {
    @Override
    public void send(Alert alert) {
        System.out.println(alert.toMessage());
    }
}
