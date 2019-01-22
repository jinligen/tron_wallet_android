package prochain.com.tronbox.utils.MessageBus;

/**
 * Created by alex on 2017/6/9.
 */

public class MessageEvent {

    public final String message;
    public String param1;
    public String param2;
    public String param3;
    public String param4;
    public String method;

    public EventType type;

    public MessageEvent(String message, EventType type) {
        this.message = message;
        this.type = type;
    }
}
