package tw.waterballsa.challenge.infrastructure.cqrs;

public interface QueryHandler<TQuery extends Query<TResult>, TResult> {
    TResult handle(TQuery query);
}
