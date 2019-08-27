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

import net.minecraftforge.event.terraingen.OreGenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * *GENERATORS DOESN'T RUN UNDER SINGLETON MODE!*
 * An listener interface of {@link OreGenEvent}
 */
public interface OreGenerator {
    /**
     * Get an {@link OreGenerator} which has been configured by mod automatically.
     * @return An {@link OreGenerator}
     */
    static OreGenerator getGenerator(){
        return new OreGeneratorDefault();
    }

    @SubscribeEvent
    default void pre(OreGenEvent.Pre event){}

    @SubscribeEvent
    default void onGenerateMinable(OreGenEvent.GenerateMinable event){}

    @SubscribeEvent
    default void post(OreGenEvent.Post event){}
}
