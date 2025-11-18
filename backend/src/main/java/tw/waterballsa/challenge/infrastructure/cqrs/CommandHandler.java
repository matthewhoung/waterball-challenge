package tw.waterballsa.challenge.infrastructure.cqrs;

public interface CommandHandler<TCommand extends Command<TResult>, TResult> {
    TResult handle(TCommand command);
}
