package sausages_material.util;

import com.google.common.collect.Lists;
import net.minecraft.util.IStringSerializable;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.EventBus;
import net.minecraftforge.fml.common.eventhandler.IEventExceptionHandler;
import net.minecraftforge.fml.common.eventhandler.IEventListener;
import org.apache.logging.log4j.Logger;
import sausages_material.SausagesMaterial;

@SuppressWarnings("WeakerAccess")
public class EventExceptionHandler implements IEventExceptionHandler {

    private static final EventExceptionHandler DEFAULT = EventExceptionHandler.newInstance(SausagesMaterial.logger);
    private final Logger logger;

    private EventExceptionHandler(Logger logger) {
        this.logger = logger;
    }


    public static EventExceptionHandler newInstance(Logger logger) {
        return new EventExceptionHandler(logger);
    }

    public static EventExceptionHandler getDefault() {
        return DEFAULT;
    }

    /**
     * Fired when a EventListener throws an exception for the specified event on the event bus.
     * After this function returns, the original Throwable will be propagated upwards.
     *
     * @param bus       The bus the event is being fired on
     * @param event     The event that is being fired
     * @param listeners All listeners that are listening for this event, in order
     * @param index     Index for the current listener being fired.
     * @param throwable The throwable being thrown
     */
    @Override
    public void handleException(EventBus bus, Event event, IEventListener[] listeners, int index, Throwable throwable) {
        logger.error("***************************************");
        logger.error("*Encountered problem when firing event!");
        logger.error("*EventBus: {}", bus instanceof IStringSerializable ? SMUtils.checkAndCast(IStringSerializable.class, bus).getName() : bus.toString());
        logger.error("*EventListeners:{}", Lists.newArrayList(listeners).toString());
        logger.error("*Problem happened on:{}",listeners[index]);
        printException(throwable, "");
        logger.error("***************************************");
    }

    private void printException(Throwable throwable, String addBefore) {
        logger.error("*{}Exception: {}",addBefore,throwable.getClass().getName() + (throwable.getMessage()==null?"":":"+throwable.getMessage()));
        logger.error("*{}StackTrace:",addBefore);
        StackTraceElement[] elements = throwable.getStackTrace();
        for (StackTraceElement element : elements) {
            logger.error("*{}    at {}",addBefore,element.toString());
        }
        Throwable cause = throwable.getCause();
        if (cause != null) {
            logger.error("*{}Caused by:",addBefore);
            printException(cause, addBefore.concat("    "));
        }
        Throwable[] suppressedExceptions = throwable.getSuppressed();
        if (suppressedExceptions.length > 0) {
            for (Throwable suppressed : suppressedExceptions) {
                logger.error("*{}Suppressed by:",addBefore);
                printException(suppressed, addBefore.concat("    "));
            }
        }
    }
}