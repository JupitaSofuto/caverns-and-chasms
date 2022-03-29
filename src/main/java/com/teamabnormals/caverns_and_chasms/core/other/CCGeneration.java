package com.teamabnormals.caverns_and_chasms.core.other;

import com.teamabnormals.blueprint.core.other.tags.BlueprintBiomeTags;
import com.teamabnormals.blueprint.core.util.TagUtil;
import com.teamabnormals.caverns_and_chasms.core.CavernsAndChasms;
import com.teamabnormals.caverns_and_chasms.core.other.tags.CCBiomeTags;
import com.teamabnormals.caverns_and_chasms.core.registry.CCEntityTypes;
import com.teamabnormals.caverns_and_chasms.core.registry.CCFeatures.CCPlacedFeatures;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.common.world.MobSpawnSettingsBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mod.EventBusSubscriber(modid = CavernsAndChasms.MOD_ID)
public class CCGeneration {

	@SubscribeEvent
	public static void onBiomeLoad(BiomeLoadingEvent event) {
		ResourceLocation name = event.getName();
		BiomeGenerationSettingsBuilder generation = event.getGeneration();
		MobSpawnSettingsBuilder spawns = event.getSpawns();
		Biome biome = ForgeRegistries.BIOMES.getValue(name);

		if (name == null) return;

		List<Holder<PlacedFeature>> ores = generation.getFeatures(Decoration.UNDERGROUND_ORES);
		List<Holder<PlacedFeature>> decorations = generation.getFeatures(Decoration.UNDERGROUND_DECORATION);

		if (TagUtil.isTagged(biome, CCBiomeTags.HAS_SOUL_SILVER_ORE)) {
			removeGoldOre(decorations);
			generation.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, CCPlacedFeatures.ORE_SILVER_SOUL);
		}

		if (TagUtil.isTagged(biome, CCBiomeTags.HAS_EXTRA_SILVER_ORE)) {
			generation.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, CCPlacedFeatures.ORE_SILVER_EXTRA);
		}

		if (TagUtil.isTagged(biome, CCBiomeTags.HAS_SPINEL_ORE)) {
			removeLapisOre(ores);
			generation.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, CCPlacedFeatures.ORE_SPINEL);
			generation.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, CCPlacedFeatures.ORE_SPINEL_BURIED);
		}

		if (TagUtil.isTagged(biome, CCBiomeTags.HAS_MIME)) {
			spawns.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(CCEntityTypes.MIME.get(), 150, 1, 1));
		}

		if (TagUtil.isTagged(biome, BlueprintBiomeTags.IS_OVERWORLD)) {
			if (event.getClimate().temperature <= 0.3D || TagUtil.isTagged(biome, CCBiomeTags.HAS_SILVER_ORE)) {
				removeGoldOre(ores);
				generation.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, CCPlacedFeatures.ORE_SILVER);
				generation.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, CCPlacedFeatures.ORE_SILVER_LOWER);
			}

			removeDirtOre(ores);
			generation.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, CCPlacedFeatures.ORE_ROCKY_DIRT);
			generation.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, CCPlacedFeatures.ORE_FRAGILE_STONE);

			if (!TagUtil.isTagged(biome, CCBiomeTags.WITHOUT_CAVEFISH_SPAWNS))
				spawns.addSpawn(MobCategory.WATER_CREATURE, new MobSpawnSettings.SpawnerData(CCEntityTypes.CAVEFISH.get(), 25, 4, 7));
		}
	}

	public static void removeGoldOre(List<Holder<PlacedFeature>> features) {
		List<Holder<PlacedFeature>> toRemove = new ArrayList<>();
		for (Holder<PlacedFeature> placedFeature : features) {
			for (ConfiguredFeature<?, ?> feature : placedFeature.value().getFeatures().collect(Collectors.toList())) {
				if (feature.config() instanceof OreConfiguration ore) {
					ore.targetStates.forEach((targetBlockState -> {
						if (targetBlockState.state.is(Blocks.GOLD_ORE) || targetBlockState.state.is(Blocks.DEEPSLATE_GOLD_ORE) || targetBlockState.state.is(Blocks.NETHER_GOLD_ORE)) {
							toRemove.add(placedFeature);
						}
					}));
				}
			}
		}
		toRemove.forEach(features::remove);
	}

	public static void removeLapisOre(List<Holder<PlacedFeature>> features) {
		List<Holder<PlacedFeature>> toRemove = new ArrayList<>();
		for (Holder<PlacedFeature> placedFeature : features) {
			for (ConfiguredFeature<?, ?> feature : placedFeature.value().getFeatures().collect(Collectors.toList())) {
				if (feature.config() instanceof OreConfiguration ore) {
					ore.targetStates.forEach((targetBlockState -> {
						if (targetBlockState.state.is(Blocks.LAPIS_ORE) || targetBlockState.state.is(Blocks.DEEPSLATE_LAPIS_ORE)) {
							toRemove.add(placedFeature);
						}
					}));
				}
			}
		}

		toRemove.forEach(features::remove);
	}

	public static void removeDirtOre(List<Holder<PlacedFeature>> features) {
		List<Holder<PlacedFeature>> toRemove = new ArrayList<>();
		for (Holder<PlacedFeature> placedFeature : features) {
			for (ConfiguredFeature<?, ?> feature : placedFeature.value().getFeatures().collect(Collectors.toList())) {
				if (feature.config() instanceof OreConfiguration ore) {
					ore.targetStates.forEach((targetBlockState -> {
						if (targetBlockState.state.is(Blocks.DIRT)) {
							toRemove.add(placedFeature);
						}
					}));
				}
			}
		}

		toRemove.forEach(features::remove);
	}
}
