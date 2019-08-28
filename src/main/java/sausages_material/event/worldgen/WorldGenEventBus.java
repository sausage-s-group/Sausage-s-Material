package sausages_material.event.worldgen;

import com.google.common.collect.Lists;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.EventBus;
import net.minecraftforge.fml.common.eventhandler.IEventExceptionHandler;
import net.minecraftforge.fml.common.eventhandler.IEventListener;
import sausage_core.api.annotation.LoadClass;
import sausages_material.event.worldgen.ore.OreGenerator;
import sausages_material.util.EventBusBindings;

import static sausages_material.SausagesMaterial.logger;

@LoadClass(when = LoadClass.When.PRE_INIT)
public class WorldGenEventBus {
    public static final EventBus ORE_GEN_BUS = new EventBus(EventExceptionHandler.getInstance());
    public static final EventBus TERRAIN_GEN_BUS = new EventBus(EventExceptionHandler.getInstance());
    public static final class EventExceptionHandler implements IEventExceptionHandler{
        private EventExceptionHandler(){}

        private static final EventExceptionHandler INSTANCE = new EventExceptionHandler();

        public static EventExceptionHandler getInstance() {
            return new EventExceptionHandler();
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
            logger.error("*EventBus:"+(bus.equals(ORE_GEN_BUS)?"ORE_GEN_BUS":"TERRAIN_GEN_BUS"));
            logger.error("*EventListeners:"+ Lists.newArrayList(listeners).toString());
            logger.error("*Problem happened on:"+listeners[index]);
            printException(throwable,"");
            logger.error("***************************************");
        }

        private void printException(Throwable throwable,String addBefore) {
            logger.error("*"+addBefore+"Exception: "+throwable.getClass().getName()+":"+throwable.getMessage());
            logger.error("*"+addBefore+"StackTrace:");
            StackTraceElement[] elements = throwable.getStackTrace();
            for(StackTraceElement element:elements){
                logger.error("*"+addBefore+"    at "+element.toString());
            }
            Throwable cause = throwable.getCause();
            if(cause!=null) {
                logger.error("*"+addBefore+"Caused by:");
                printException(cause, addBefore + "    ");
            }
            Throwable suppresseds[] = throwable.getSuppressed();
            if(suppresseds.length>0) {
                for (Throwable suppressed : suppresseds) {
                    logger.error("*"+addBefore+"Suppressed by:");
                    printException(suppressed, addBefore);
                }
            }
        }
    }
    static {
        EventBusBindings.destination(ORE_GEN_BUS).listen(MinecraftForge.ORE_GEN_BUS);
        EventBusBindings.destination(TERRAIN_GEN_BUS).listen(MinecraftForge.TERRAIN_GEN_BUS);
        ORE_GEN_BUS.register(OreGenerator.getGenerator());
    }
}
