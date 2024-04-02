package com.teamabnormals.caverns_and_chasms.core.registry;

import com.teamabnormals.caverns_and_chasms.client.particle.*;
import com.teamabnormals.caverns_and_chasms.client.particle.SilverParticle.DamageProvider;
import com.teamabnormals.caverns_and_chasms.client.particle.SilverParticle.SparkProvider;
import com.teamabnormals.caverns_and_chasms.core.CavernsAndChasms;
import net.minecraft.client.particle.FlameParticle;
import net.minecraft.client.particle.HugeExplosionParticle;
import net.minecraft.client.particle.PlayerCloudParticle;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@EventBusSubscriber(modid = CavernsAndChasms.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class CCParticleTypes {
	public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, CavernsAndChasms.MOD_ID);

	public static final RegistryObject<SimpleParticleType> CUPRIC_FIRE_FLAME = registerSimpleParticleType(false, "cupric_fire_flame");
	public static final RegistryObject<SimpleParticleType> SMALL_CUPRIC_FIRE_FLAME = registerSimpleParticleType(false, "small_cupric_fire_flame");
	public static final RegistryObject<SimpleParticleType> MIME_ENERGY = registerSimpleParticleType(true, "mime_energy");
	public static final RegistryObject<SimpleParticleType> MIME_SPARK = registerSimpleParticleType(true, "mime_spark");
	public static final RegistryObject<SimpleParticleType> SILVER_HIT = registerSimpleParticleType(true, "silver_hit");
	public static final RegistryObject<SimpleParticleType> SILVER_SPARK = registerSimpleParticleType(false, "silver_spark");
	public static final RegistryObject<SimpleParticleType> STONE_DUST = registerSimpleParticleType(true, "stone_dust");
	public static final RegistryObject<SimpleParticleType> DEEPSLATE_DUST = registerSimpleParticleType(true, "deepslate_dust");
	public static final RegistryObject<SimpleParticleType> STONE_CHIP = registerSimpleParticleType(false, "stone_chip");
	public static final RegistryObject<SimpleParticleType> DEEPSLATE_CHIP = registerSimpleParticleType(false, "deepslate_chip");
	public static final RegistryObject<SimpleParticleType> LAVA_LAMP_SMOKE = registerSimpleParticleType(true, "lava_lamp_smoke");
	public static final RegistryObject<SimpleParticleType> FLOODLIGHT_DUST = registerSimpleParticleType(false, "floodlight_dust");
	public static final RegistryObject<SimpleParticleType> EXPOSED_FLOODLIGHT_DUST = registerSimpleParticleType(false, "exposed_floodlight_dust");
	public static final RegistryObject<SimpleParticleType> WEATHERED_FLOODLIGHT_DUST = registerSimpleParticleType(false, "weathered_floodlight_dust");
	public static final RegistryObject<SimpleParticleType> OXIDIZED_FLOODLIGHT_DUST = registerSimpleParticleType(false, "oxidized_floodlight_dust");
	public static final RegistryObject<SimpleParticleType> SPINEL_BOOM_CIRCLE = registerSimpleParticleType(true, "spinel_boom_circle");
	public static final RegistryObject<SimpleParticleType> SPINEL_BOOM_STAR = registerSimpleParticleType(true, "spinel_boom_star");
	public static final RegistryObject<SimpleParticleType> SPINEL_BOOM_EMITTER = registerSimpleParticleType(true, "spinel_boom_emitter");
	public static final RegistryObject<SimpleParticleType> GOLEM_NOTE = registerSimpleParticleType(true, "golem_note");

	private static RegistryObject<SimpleParticleType> registerSimpleParticleType(boolean alwaysShow, String name) {
		return PARTICLE_TYPES.register(name, () -> new SimpleParticleType(alwaysShow));
	}

	@EventBusSubscriber(modid = CavernsAndChasms.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
	public static class RegisterParticles {
		@SubscribeEvent
		public static void registerParticleTypes(RegisterParticleProvidersEvent event) {
			event.registerSpriteSet(CUPRIC_FIRE_FLAME.get(), FlameParticle.Provider::new);
			event.registerSpriteSet(SMALL_CUPRIC_FIRE_FLAME.get(), FlameParticle.SmallFlameProvider::new);
			event.registerSpriteSet(MIME_ENERGY.get(), MimeEnergyParticle.Provider::new);
			event.registerSpriteSet(MIME_SPARK.get(), PlayerCloudParticle.Provider::new);
			event.registerSpriteSet(SILVER_HIT.get(), DamageProvider::new);
			event.registerSpriteSet(SILVER_SPARK.get(), SparkProvider::new);
			event.registerSpriteSet(STONE_DUST.get(), StoneDustParticle.Provider::new);
			event.registerSpriteSet(DEEPSLATE_DUST.get(), StoneDustParticle.Provider::new);
			event.registerSpriteSet(STONE_CHIP.get(), ChipParticle.Provider::new);
			event.registerSpriteSet(DEEPSLATE_CHIP.get(), ChipParticle.Provider::new);
			event.registerSpriteSet(LAVA_LAMP_SMOKE.get(), LavaLampSmokeParticle.Provider::new);
			event.registerSpriteSet(FLOODLIGHT_DUST.get(), FloodlightDustParticle.Provider::new);
			event.registerSpriteSet(EXPOSED_FLOODLIGHT_DUST.get(), FloodlightDustParticle.Provider::new);
			event.registerSpriteSet(WEATHERED_FLOODLIGHT_DUST.get(), FloodlightDustParticle.Provider::new);
			event.registerSpriteSet(OXIDIZED_FLOODLIGHT_DUST.get(), FloodlightDustParticle.Provider::new);
			event.registerSpriteSet(SPINEL_BOOM_CIRCLE.get(), HugeExplosionParticle.Provider::new);
			event.registerSpriteSet(SPINEL_BOOM_STAR.get(), HugeExplosionParticle.Provider::new);
			event.registerSpecial(SPINEL_BOOM_EMITTER.get(), new SpinelBoomParticle.Provider());
			event.registerSpriteSet(GOLEM_NOTE.get(), GolemNoteParticle.Provider::new);
		}
	}
}
