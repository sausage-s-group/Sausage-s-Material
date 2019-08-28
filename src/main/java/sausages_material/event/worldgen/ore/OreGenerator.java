/*
  Copyright 2019 langyo<langyo.china@gmail.com> and contributors

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

      http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
 */

package sausages_material.event.worldgen.ore;

import com.google.common.collect.Sets;
import javafx.beans.property.ReadOnlyObjectWrapper;
import net.minecraftforge.event.terraingen.OreGenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import sausage_core.api.annotation.AutoCall;
import sausage_core.api.annotation.LoadClass;
import sausages_material.api_support.APISupport;
import sausages_material.config.ConfigurationCustomization;
import sausages_material.config.ConfigurationGeneral;

import java.util.Collections;
import java.util.Set;
import java.util.function.Supplier;

/**
 * *GENERATORS DOESN'T RUN UNDER SINGLETON MODE!*
 * An listener interface of {@link OreGenEvent}
 */
@LoadClass(when = LoadClass.When.PRE_INIT)
public interface OreGenerator {
    @AutoCall(when = AutoCall.When.PRE_INIT)
    Set<Runnable> tasks = Sets.newHashSet(Container.StaticInitializer::new);
    final class Container{
        @LoadClass(when = LoadClass.When.PRE_INIT)
        private static final class StaticInitializer{
            public static final Supplier<OreGenerator> GEN;

            static {
                GEN = new Supplier<OreGenerator>() {
                    private OreGenerator singleton;

                    @Override
                    public OreGenerator get() {
                        if(!singletonOreGen){
                            return useOverridableOreGen?OreGeneratorOverride.override(APISupport.getOreGenConfiguration()):new OreGeneratorDefault();
                        } else if(singleton==null) {
                            singleton = useOverridableOreGen?OreGeneratorOverride.override(APISupport.getOreGenConfiguration()):new OreGeneratorDefault();
                        }
                        return singleton;
                    }
                };
            }
            private static final boolean useOverridableOreGen = ConfigurationCustomization.useOverridableOreGen();
            private static final boolean singletonOreGen = ConfigurationGeneral.isSingletonOreGen();
        }
    }

    /**
     * Get an {@link OreGenerator}.
     * @return An {@link OreGenerator}
     */
    static OreGenerator getGenerator(){
        return Container.StaticInitializer.GEN.get();
    }

    @SubscribeEvent
    default void pre(OreGenEvent.Pre event){}

    @SubscribeEvent
    default void onGenerateMinable(OreGenEvent.GenerateMinable event){}

    @SubscribeEvent
    default void post(OreGenEvent.Post event){}
}
