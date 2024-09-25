package fr.atesab.xray.utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.server.IntegratedServer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class LocationUtils {
	
	private static final ThreadLocal<NumberFormat> localTwoDigitNfFormat = ThreadLocal.withInitial(() -> new DecimalFormat("00"));

	public static BlockPos getLookingBlockPos(Minecraft mc) {
		HitResult hitResult = mc.hitResult;
		if (hitResult instanceof BlockHitResult bir) {
			return bir.getBlockPos();
		} else if (hitResult != null) {
			Vec3 pos = hitResult.getLocation();
			return new BlockPos((int) pos.x, (int) pos.y, (int) pos.z);
		} else {
			return new BlockPos(0, 0, 0);
		}
	}
	
	public static BlockPos getLookingFaceBlockPos(Minecraft mc, LocalPlayer player) {
		HitResult target = mc.hitResult;
		if (target == null) {
			return new BlockPos(0, 0, 0);
		}
		Vec3 pos = target.getLocation();
		if (pos.x == (int)pos.x) {
			return getLookingBlockPos(mc).offset(player.getX() < pos.x ? -1 : 1, 0, 0);
		} else if (pos.y == (int)pos.y) {
			return getLookingBlockPos(mc).offset(0, player.getY() < pos.y ? -1 : 1, 0);
		} else if (pos.z == (int)pos.z) {
			return getLookingBlockPos(mc).offset(0, 0, player.getZ() < pos.z ? -1 : 1);
		} else {
			return new BlockPos((int) pos.x, (int) pos.y, (int) pos.z);
		}
	}
	
	public static float getBlockDestroyProgress(Player player,ClientLevel level,BlockPos blockpos) {
		return level.getBlockState(blockpos).getDestroyProgress(player, level, blockpos);
	}
	
	public static NumberFormat getTwoDigitNumberFormat() {
		return localTwoDigitNfFormat.get();
	}

	public static String isSlimeChunk(Minecraft mc, ChunkPos chunk) {
		IntegratedServer server = mc.getSingleplayerServer();
		if (server == null) {
			return "false";
		}
		return String.valueOf(WorldgenRandom.seedSlimeChunk(chunk.x, chunk.z,
				server.getWorldData().worldGenOptions().seed(), 987234911L).nextInt(10) == 0);
	}
	
	public static String getDurabilityOrFoodData(ItemStack item) {
		if (item.isDamageableItem()) {
			return String.valueOf(getRemainDurability(item));
		} else if (item.getItem().components().has(DataComponents.FOOD)) {
			return String.valueOf(item.getFoodProperties(null).nutrition()) + "(" + String.format("%.1f",getAddSaturation(item)) + ")";
		} else {
			return "-";
		}
	}

	public static String getMaxDurabilityOrAfterFoodData(ItemStack item,int currentNutrition, float currentSaturation) {
		if (item.isDamageableItem()) {
			return String.valueOf(item.getMaxDamage());
		} else if (item.getItem().components().has(DataComponents.FOOD)) {
			int afterNutrition = Math.min(currentNutrition + item.getFoodProperties(null).nutrition(), 20);
			float afterSaturation = Math.min(currentSaturation + item.getFoodProperties(null).saturation(), afterNutrition);
			return String.valueOf(afterNutrition) + "(" + String.format("%.1f",afterSaturation) + ")";
		} else {
			return "-";
		}
	}

	public static int getRemainDurability(ItemStack item) {
		return item.getMaxDamage() - item.getDamageValue();
	}

	public static float getAddSaturation(ItemStack item) {
		return item.getFoodProperties(null).saturation();
	}
	
	public static String getCorrectToolText(BlockState blockstate) {
		TagKey<Block> tier = filterBlockTag(blockstate, BlockTags.NEEDS_DIAMOND_TOOL, BlockTags.NEEDS_IRON_TOOL, BlockTags.NEEDS_STONE_TOOL);
		TagKey<Block> tool = filterBlockTag(blockstate, BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.MINEABLE_WITH_AXE, BlockTags.MINEABLE_WITH_SHOVEL, BlockTags.MINEABLE_WITH_HOE, BlockTags.SWORD_EFFICIENT);
		StringBuilder optionValue = new StringBuilder();
		optionValue.append(Component.translatable("x13.mod.location.opt.block.requiredTool",Component.translatable("x13.mod.location.opt.block.requiredTool." + convertNameFromBlockTagKey(tool)).getString()).getString());
		if (tier != null) {
			optionValue.append(Component.translatable("x13.mod.location.opt.block.requiredTier",Component.translatable("x13.mod.location.opt.block.requiredTier." + convertNameFromBlockTagKey(tier)).getString()).getString());
		}
		return optionValue.toString();
	}

	@SafeVarargs
	public static TagKey<Block> filterBlockTag(BlockState blockstate, TagKey<Block>... keys) {
		for (int i = 0;i < keys.length;i++ ) {
			if (blockstate.is(keys[i]))
				return keys[i];
		}
		return null;
	}
	
	public static String convertNameFromBlockTagKey(TagKey<Block> key) {
		if (key == null)                                { return null; } else
		if (key.equals(BlockTags.NEEDS_DIAMOND_TOOL))    { return "diamond"; } else
		if (key.equals(BlockTags.NEEDS_IRON_TOOL))       { return "iron"; } else
		if (key.equals(BlockTags.NEEDS_STONE_TOOL))      { return "stone"; } else
		if (key.equals(BlockTags.MINEABLE_WITH_PICKAXE)) { return "pickaxe"; } else
		if (key.equals(BlockTags.MINEABLE_WITH_AXE))     { return "axe"; } else
		if (key.equals(BlockTags.MINEABLE_WITH_SHOVEL))  { return "shovel"; } else
		if (key.equals(BlockTags.MINEABLE_WITH_HOE))     { return "hoe"; } else
		if (key.equals(BlockTags.SWORD_EFFICIENT))       { return "sword"; } else {
			return null;
		}
	}
}
