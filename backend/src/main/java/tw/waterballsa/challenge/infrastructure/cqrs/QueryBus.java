package tw.waterballsa.challenge.infrastructure.cqrs;

import org.springframework.stereotype.Component;
import java.util.Map;
import java.util.HashMap;

@Component
public class QueryBus {

    private final Map<Class<?>, QueryHandler<?, ?>> handlers = new HashMap<>();

    public <TQuery extends Query<TResult>, TResult> void register(
            Class<TQuery> queryClass,
            QueryHandler<TQuery, TResult> handler) {
        handlers.put(queryClass, handler);
    }

    @SuppressWarnings("unchecked")
    public <TQuery extends Query<TResult>, TResult> TResult execute(TQuery query) {
        QueryHandler<TQuery, TResult> handler =
                (QueryHandler<TQuery, TResult>) handlers.get(query.getClass());

        if (handler == null) {
            throw new IllegalStateException(
                    "No handler registered for query: " + query.getClass().getName()
            );
        }

        return handler.handle(query);
    }
}