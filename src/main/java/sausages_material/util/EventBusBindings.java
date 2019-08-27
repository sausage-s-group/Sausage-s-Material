package sausages_material.util;

import com.google.common.eventbus.Subscribe;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Optional;
import java.util.function.Predicate;

@SuppressWarnings({"UnstableApiUsage", "unused"})
public interface EventBusBindings {

    static EventBusBindings destination(com.google.common.eventbus.EventBus eventBus){
        return new Container.GoogleEventBusListener(eventBus);
    }

    static EventBusBindings destination(net.minecraftforge.fml.common.eventhandler.EventBus eventBus){
        return new Container.ForgeEventBusListener(eventBus);
    }

    static void bindWith(com.google.common.eventbus.EventBus bus1, net.minecraftforge.fml.common.eventhandler.EventBus bus2, Predicate<Event> FtoG, Predicate<Object> GtoF){
        destination(bus1).listen(bus2,FtoG);
        destination(bus2).listen(bus1,GtoF);
    }

    void listen(com.google.common.eventbus.EventBus eventBus);
    void listen(com.google.common.eventbus.EventBus eventBus, Predicate<Object> predicate);

    void listen(net.minecraftforge.fml.common.eventhandler.EventBus eventBus);
    void listen(net.minecraftforge.fml.common.eventhandler.EventBus eventBus, Predicate<Event> predicate);

    final class Container{
        private static final class ForgeEventBusPoster{
            private net.minecraftforge.fml.common.eventhandler.EventBus bus;
            private Predicate<Event> predicate;

            private ForgeEventBusPoster(net.minecraftforge.fml.common.eventhandler.EventBus bus,Predicate<Event> predicate){
                this.bus = bus;
                this.predicate = Optional.ofNullable(predicate).orElse($->true);
            }

            @SubscribeEvent
            public void on(Event event){
                if(predicate.test(event)){
                    bus.post(event);
                }
            }

            @Subscribe
            public void on(Object event){
                if(event instanceof Event&&predicate.test((Event) event)){
                    bus.post((Event) event);
                }
            }
        }
        private static final class GoogleEventBusPoster{
            private com.google.common.eventbus.EventBus bus;
            private Predicate<Object> predicate;

            private GoogleEventBusPoster(com.google.common.eventbus.EventBus bus,Predicate<Object> predicate){
                this.bus = bus;
                this.predicate = Optional.ofNullable(predicate).orElse($->true);
            }

            @SubscribeEvent
            public void on(Event event){
                if(predicate.test(event)){
                    bus.post(event);
                }
            }

            @Subscribe
            public void on(Object event){
                if(predicate.test(event)){
                    bus.post(event);
                }
            }
        }
        private static class GoogleEventBusListener implements EventBusBindings {

            private final com.google.common.eventbus.EventBus bus;

            private GoogleEventBusListener(com.google.common.eventbus.EventBus eventBus){
                bus = eventBus;
            }

            @Override
            public void listen(com.google.common.eventbus.EventBus eventBus) {
                eventBus.register(new GoogleEventBusPoster(bus,$->true));
            }

            @Override
            public void listen(com.google.common.eventbus.EventBus eventBus, Predicate<Object> predicate) {
                eventBus.register(new GoogleEventBusPoster(bus,predicate));
            }

            @Override
            public void listen(net.minecraftforge.fml.common.eventhandler.EventBus eventBus) {
                eventBus.register(new GoogleEventBusPoster(bus,$->true));
            }

            @Override
            public void listen(net.minecraftforge.fml.common.eventhandler.EventBus eventBus, Predicate<Event> predicate) {
                eventBus.register(new GoogleEventBusPoster(bus,obj->obj instanceof Event&&predicate.test((Event) obj)));
            }
        }

        public static class ForgeEventBusListener implements EventBusBindings {

            private final net.minecraftforge.fml.common.eventhandler.EventBus bus;

            private ForgeEventBusListener(net.minecraftforge.fml.common.eventhandler.EventBus eventBus) {
                bus = eventBus;
            }

            @Override
            public void listen(com.google.common.eventbus.EventBus eventBus) {
                eventBus.register(new ForgeEventBusPoster(bus, $->true));
            }

            @Override
            public void listen(com.google.common.eventbus.EventBus eventBus, Predicate<Object> predicate) {
                eventBus.register(new ForgeEventBusPoster(bus, predicate::test));
            }

            @Override
            public void listen(net.minecraftforge.fml.common.eventhandler.EventBus eventBus) {
                eventBus.register(new ForgeEventBusPoster(bus, $->true));
            }

            @Override
            public void listen(net.minecraftforge.fml.common.eventhandler.EventBus eventBus, Predicate<Event> predicate) {
                eventBus.register(new ForgeEventBusPoster(bus, predicate));
            }
        }
    }
}
