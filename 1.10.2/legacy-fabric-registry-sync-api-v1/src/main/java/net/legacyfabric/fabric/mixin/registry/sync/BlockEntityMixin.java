/*
 * Copyright (c) 2020 - 2022 Legacy Fabric
 * Copyright (c) 2016 - 2022 FabricMC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.legacyfabric.fabric.mixin.registry.sync;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import net.minecraft.block.entity.BlockEntity;

import net.legacyfabric.fabric.impl.registry.sync.remappers.BlockEntityRegistryRemapper;
import net.legacyfabric.fabric.impl.registry.sync.remappers.RegistryRemapper;
import net.legacyfabric.fabric.impl.registry.util.MapBasedRegistry;

@Mixin(BlockEntity.class)
public class BlockEntityMixin {
	@ModifyArg(method = "create", at = @At(value = "INVOKE", remap = false, target = "Ljava/util/Map;get(Ljava/lang/Object;)Ljava/lang/Object;"))
	private static Object replaceVanillaId(Object oldKey) {
		BlockEntityRegistryRemapper registryRemapper = (BlockEntityRegistryRemapper) RegistryRemapper.<Class<? extends BlockEntity>>getRegistryRemapper(RegistryRemapper.BLOCK_ENTITIES);
		MapBasedRegistry<String, Class<? extends BlockEntity>> registry = (MapBasedRegistry<String, Class<? extends BlockEntity>>) registryRemapper.getRegistry();

		return registry.getNewId(oldKey.toString());
	}
}
