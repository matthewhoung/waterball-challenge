package tw.waterballsa.challenge.infrastructure.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.annotation.PostConstruct;
import tw.waterballsa.challenge.infrastructure.cqrs.*;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

@Configuration
public class CqrsConfig {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private CommandBus commandBus;

    @Autowired
    private QueryBus queryBus;

    @PostConstruct
    public void registerHandlers() {
        registerCommandHandlers();
        registerQueryHandlers();
    }

    @SuppressWarnings("unchecked")
    private void registerCommandHandlers() {
        Map<String, CommandHandler> handlers = applicationContext.getBeansOfType(CommandHandler.class);

        for (CommandHandler<?, ?> handler : handlers.values()) {
            Class<?> commandClass = getCommandClass(handler.getClass());
            if (commandClass != null) {
                commandBus.register((Class) commandClass, handler);
            }
        }
    }

    @SuppressWarnings("unchecked")
    private void registerQueryHandlers() {
        Map<String, QueryHandler> handlers = applicationContext.getBeansOfType(QueryHandler.class);

        for (QueryHandler<?, ?> handler : handlers.values()) {
            Class<?> queryClass = getQueryClass(handler.getClass());
            if (queryClass != null) {
                queryBus.register((Class) queryClass, handler);
            }
        }
    }

    private Class<?> getCommandClass(Class<?> handlerClass) {
        return getGenericClass(handlerClass, CommandHandler.class);
    }

    private Class<?> getQueryClass(Class<?> handlerClass) {
        return getGenericClass(handlerClass, QueryHandler.class);
    }

    private Class<?> getGenericClass(Class<?> handlerClass, Class<?> interfaceClass) {
        for (Type type : handlerClass.getGenericInterfaces()) {
            if (type instanceof ParameterizedType paramType) {
                if (paramType.getRawType().equals(interfaceClass)) {
                    return (Class<?>) paramType.getActualTypeArguments()[0];
                }
            }
        }
        return null;
    }
}