package tw.waterballsa.challenge.infrastructure.cqrs;

import org.springframework.stereotype.Component;
import java.util.Map;
import java.util.HashMap;

@Component
public class CommandBus {

    private final Map<Class<?>, CommandHandler<?, ?>> handlers = new HashMap<>();

    public <TCommand extends Command<TResult>, TResult> void register(
            Class<TCommand> commandClass,
            CommandHandler<TCommand, TResult> handler) {
        handlers.put(commandClass, handler);
    }

    @SuppressWarnings("unchecked")
    public <TCommand extends Command<TResult>, TResult> TResult execute(TCommand command) {
        CommandHandler<TCommand, TResult> handler =
                (CommandHandler<TCommand, TResult>) handlers.get(command.getClass());

        if (handler == null) {
            throw new IllegalStateException(
                    "No handler registered for command: " + command.getClass().getName()
            );
        }

        return handler.handle(command);
    }
}
