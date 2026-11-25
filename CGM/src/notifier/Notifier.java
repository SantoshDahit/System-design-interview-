package notifier;

import entity.Alert;

// Easy to swap later: ConsoleNotifier now, PushNotifier / SmsNotifier later.
public interface Notifier {
    void send(Alert alert);
}
