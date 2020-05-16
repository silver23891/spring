package ru.geekbrains.server;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import ru.geekbrains.server.persistance.UserRepository;

@Component
public class BeanPP implements BeanPostProcessor {
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        // В данном методе просто возвращаем бин
        return bean;
    }

    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("Создан бин " + beanName);
        if (bean instanceof ChatServer) {
            ((ChatServer) bean).start(7777);
        }
        return bean;
    }

}
