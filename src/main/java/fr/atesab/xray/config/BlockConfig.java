package fr.atesab.xray.config;

import java.util.Objects;

import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.google.gson.annotations.Expose;

import fr.atesab.xray.SideRenderer;
import fr.atesab.xray.XrayMain;
import fr.atesab.xray.color.EnumElement;
import fr.atesab.xray.view.ViewMode;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class BlockConfig extends AbstractModeConfig implements SideRenderer, Cloneable {
    public enum Template implements EnumElement {
        // @formatter:off
        BLANK("x13.mod.template.blank", Items.PAPER, new BlockConfig()),
        XRAY("x13.mod.template.xray", Blocks.DIAMOND_ORE, new BlockConfig(
                GLFW.GLFW_KEY_X,
                0,
                "Xray",
                ViewMode.EXCLUSIVE,

                /* Ores */
                Blocks.COAL_ORE, Blocks.IRON_ORE, Blocks.GOLD_ORE, Blocks.DIAMOND_ORE,
                Blocks.EMERALD_ORE, Blocks.REDSTONE_ORE, Blocks.LAPIS_ORE, Blocks.NETHER_GOLD_ORE,
                Blocks.ANCIENT_DEBRIS, Blocks.NETHER_QUARTZ_ORE,

                // 1.17
                Blocks.COPPER_ORE, Blocks.DEEPSLATE_COAL_ORE, Blocks.DEEPSLATE_IRON_ORE, Blocks.DEEPSLATE_GOLD_ORE,
                Blocks.DEEPSLATE_DIAMOND_ORE, Blocks.DEEPSLATE_EMERALD_ORE, Blocks.DEEPSLATE_REDSTONE_ORE,
                Blocks.DEEPSLATE_LAPIS_ORE, Blocks.DEEPSLATE_COPPER_ORE,

                Blocks.RAW_COPPER_BLOCK, Blocks.RAW_GOLD_BLOCK, Blocks.RAW_IRON_BLOCK, Blocks.CRYING_OBSIDIAN,

                // 1.18(and 1.21.5)
                Blocks.COPPER_BLOCK, Blocks.CHISELED_COPPER, Blocks.OXIDIZED_COPPER, Blocks.WEATHERED_COPPER,
                Blocks.WAXED_COPPER_BLOCK, Blocks.WAXED_CHISELED_COPPER, Blocks.WAXED_OXIDIZED_COPPER, Blocks.WAXED_WEATHERED_COPPER,
                
                // 1.20
                Blocks.CHISELED_BOOKSHELF,

                /* Ore Blocks */
                Blocks.COAL_BLOCK, Blocks.IRON_BLOCK, Blocks.GOLD_BLOCK, Blocks.DIAMOND_BLOCK,
                Blocks.EMERALD_BLOCK, Blocks.REDSTONE_BLOCK, Blocks.LAPIS_BLOCK, Blocks.NETHERITE_BLOCK,

                /* Blocks */
                Blocks.OBSIDIAN, Blocks.BLUE_ICE, Blocks.CLAY, Blocks.BOOKSHELF,
                Blocks.SPONGE, Blocks.WET_SPONGE,

                /* Other */
                Blocks.NETHER_WART, Blocks.SPAWNER, Blocks.LAVA, Blocks.WATER,
                Blocks.TNT, Blocks.CONDUIT, Blocks.TRIAL_SPAWNER, Blocks.VAULT,

                /* Portals */
                Blocks.END_PORTAL_FRAME, Blocks.END_PORTAL, Blocks.NETHER_PORTAL,

                /* Interactive */
                Blocks.BEACON, Blocks.CHEST, Blocks.TRAPPED_CHEST, Blocks.ENDER_CHEST,
                Blocks.DISPENSER, Blocks.DROPPER, Blocks.HOPPER, Blocks.DECORATED_POT,Blocks.BARREL,

                /* Useless */
                Blocks.DRAGON_WALL_HEAD, Blocks.DRAGON_HEAD, Blocks.DRAGON_EGG,

                /* Infested (Silverfish inside) */
                Blocks.INFESTED_STONE, Blocks.INFESTED_STONE_BRICKS, Blocks.INFESTED_CRACKED_STONE_BRICKS,
                Blocks.INFESTED_COBBLESTONE, Blocks.INFESTED_CHISELED_STONE_BRICKS,
                Blocks.INFESTED_MOSSY_STONE_BRICKS)),
        CAVE("x13.mod.template.cave", Blocks.STONE, new BlockConfig(
                GLFW.GLFW_KEY_C,
                0,
                "Cave",
                ViewMode.INCLUSIVE,
                Blocks.DIRT,              Blocks.SHORT_GRASS,      Blocks.GRAVEL,          Blocks.GRASS_BLOCK,
                Blocks.DIRT_PATH,         Blocks.SAND,             Blocks.SANDSTONE,       Blocks.RED_SAND
            )),
        REDSTONE("x13.mod.template.redstone", Blocks.REDSTONE_ORE, new BlockConfig(
                GLFW.GLFW_KEY_R,
                0,
                "Redstone",
                ViewMode.EXCLUSIVE,

                Blocks.REDSTONE_BLOCK,							Blocks.REDSTONE_TORCH,
                Blocks.REDSTONE_ORE,							Blocks.DEEPSLATE_REDSTONE_ORE,
                
                Blocks.REDSTONE_WALL_TORCH,						Blocks.REDSTONE_WIRE,
                Blocks.DAYLIGHT_DETECTOR,						Blocks.OBSERVER,
                Blocks.SCULK_SENSOR,							Blocks.CALIBRATED_SCULK_SENSOR,
                Blocks.LEVER,									Blocks.TARGET,
                Blocks.CREAKING_HEART,							
                Blocks.COPPER_GOLEM_STATUE,						Blocks.EXPOSED_COPPER_GOLEM_STATUE,
                Blocks.OXIDIZED_COPPER_GOLEM_STATUE,			Blocks.WEATHERED_COPPER_GOLEM_STATUE,
                Blocks.WAXED_COPPER_GOLEM_STATUE,				Blocks.WAXED_EXPOSED_COPPER_GOLEM_STATUE,
                Blocks.WAXED_OXIDIZED_COPPER_GOLEM_STATUE,		Blocks.WAXED_WEATHERED_COPPER_GOLEM_STATUE,

                Blocks.REPEATING_COMMAND_BLOCK,					Blocks.COMMAND_BLOCK,
                Blocks.CHAIN_COMMAND_BLOCK,
                
                Blocks.COMPARATOR,								Blocks.REPEATER,
                Blocks.CHEST,									Blocks.TRAPPED_CHEST,
                Blocks.DROPPER,									Blocks.DISPENSER,
                Blocks.HOPPER,									Blocks.CRAFTER,
                Blocks.BARREL,									Blocks.FURNACE,
                Blocks.BLAST_FURNACE,							Blocks.BREWING_STAND,
                Blocks.CHISELED_BOOKSHELF,                      Blocks.DECORATED_POT,
                Blocks.SHULKER_BOX,								Blocks.BLACK_SHULKER_BOX,
                Blocks.BLUE_SHULKER_BOX,						Blocks.BROWN_SHULKER_BOX,
                Blocks.CYAN_SHULKER_BOX,						Blocks.GRAY_SHULKER_BOX,
                Blocks.GREEN_SHULKER_BOX,						Blocks.LIGHT_BLUE_SHULKER_BOX,
                Blocks.LIGHT_GRAY_SHULKER_BOX,					Blocks.LIME_SHULKER_BOX,
                Blocks.MAGENTA_SHULKER_BOX,						Blocks.ORANGE_SHULKER_BOX,
                Blocks.PINK_SHULKER_BOX,						Blocks.PURPLE_SHULKER_BOX,
                Blocks.RED_SHULKER_BOX,							Blocks.WHITE_SHULKER_BOX,
                Blocks.YELLOW_SHULKER_BOX,
                Blocks.COPPER_CHEST,							Blocks.EXPOSED_COPPER_CHEST,
                Blocks.OXIDIZED_COPPER_CHEST,					Blocks.WEATHERED_COPPER_CHEST,
                Blocks.WAXED_COPPER_CHEST,						Blocks.WAXED_EXPOSED_COPPER_CHEST,
                Blocks.WAXED_OXIDIZED_COPPER_CHEST,				Blocks.WAXED_WEATHERED_COPPER_CHEST,
                
                Blocks.PISTON,									Blocks.PISTON_HEAD,
                Blocks.MOVING_PISTON,							Blocks.STICKY_PISTON,
                Blocks.DRAGON_HEAD,								Blocks.DRAGON_WALL_HEAD,
                Blocks.PIGLIN_HEAD,								Blocks.PIGLIN_WALL_HEAD,
                Blocks.COPPER_BULB,								Blocks.EXPOSED_COPPER_BULB,
                Blocks.OXIDIZED_COPPER_BULB,					Blocks.WEATHERED_COPPER_BULB,
                Blocks.WAXED_COPPER_BULB,						Blocks.WAXED_EXPOSED_COPPER_BULB,
                Blocks.WAXED_OXIDIZED_COPPER_BULB,				Blocks.WAXED_WEATHERED_COPPER_BULB,
                Blocks.TNT,										Blocks.REDSTONE_LAMP,
                Blocks.NOTE_BLOCK,

                Blocks.ACACIA_DOOR,								Blocks.BAMBOO_DOOR,
                Blocks.BIRCH_DOOR,								Blocks.CHERRY_DOOR,
                Blocks.DARK_OAK_DOOR,							Blocks.JUNGLE_DOOR,
                Blocks.MANGROVE_DOOR,							Blocks.OAK_DOOR,
                Blocks.PALE_OAK_DOOR,							Blocks.SPRUCE_DOOR,
                Blocks.CRIMSON_DOOR,							Blocks.WARPED_DOOR,
                Blocks.IRON_DOOR,
                Blocks.COPPER_DOOR,								Blocks.EXPOSED_COPPER_DOOR,
                Blocks.OXIDIZED_COPPER_DOOR,					Blocks.WEATHERED_COPPER_DOOR,
                Blocks.WAXED_COPPER_DOOR,						Blocks.WAXED_EXPOSED_COPPER_DOOR,
                Blocks.WAXED_OXIDIZED_COPPER_DOOR,				Blocks.WAXED_WEATHERED_COPPER_DOOR,

                Blocks.ACACIA_BUTTON,							Blocks.BAMBOO_BUTTON,
                Blocks.BIRCH_BUTTON,							Blocks.CHERRY_BUTTON,
                Blocks.DARK_OAK_BUTTON,							Blocks.JUNGLE_BUTTON,
                Blocks.MANGROVE_BUTTON,							Blocks.OAK_BUTTON,
                Blocks.PALE_OAK_BUTTON,							Blocks.SPRUCE_BUTTON,
                Blocks.CRIMSON_BUTTON,							Blocks.WARPED_BUTTON,
                Blocks.STONE_BUTTON,							Blocks.POLISHED_BLACKSTONE_BUTTON,
                
                Blocks.ACACIA_TRAPDOOR,							Blocks.BAMBOO_TRAPDOOR,
                Blocks.BIRCH_TRAPDOOR,							Blocks.CHERRY_TRAPDOOR,
                Blocks.DARK_OAK_TRAPDOOR,						Blocks.JUNGLE_TRAPDOOR,
                Blocks.MANGROVE_TRAPDOOR,						Blocks.OAK_TRAPDOOR,
                Blocks.PALE_OAK_TRAPDOOR,						Blocks.SPRUCE_TRAPDOOR,
                Blocks.CRIMSON_TRAPDOOR,						Blocks.WARPED_TRAPDOOR,
                Blocks.IRON_TRAPDOOR,
                Blocks.COPPER_TRAPDOOR,							Blocks.EXPOSED_COPPER_TRAPDOOR,
                Blocks.OXIDIZED_COPPER_TRAPDOOR,				Blocks.WEATHERED_COPPER_TRAPDOOR,
                Blocks.WAXED_COPPER_TRAPDOOR,					Blocks.WAXED_EXPOSED_COPPER_TRAPDOOR,
                Blocks.WAXED_OXIDIZED_COPPER_TRAPDOOR,			Blocks.WAXED_WEATHERED_COPPER_TRAPDOOR,
                
                Blocks.ACACIA_PRESSURE_PLATE,					Blocks.BAMBOO_PRESSURE_PLATE,
                Blocks.BIRCH_PRESSURE_PLATE,					Blocks.CHERRY_PRESSURE_PLATE,
                Blocks.DARK_OAK_PRESSURE_PLATE,					Blocks.JUNGLE_PRESSURE_PLATE,
                Blocks.MANGROVE_PRESSURE_PLATE,					Blocks.OAK_PRESSURE_PLATE,
                Blocks.PALE_OAK_PRESSURE_PLATE,					Blocks.SPRUCE_PRESSURE_PLATE,
                Blocks.CRIMSON_PRESSURE_PLATE,					Blocks.WARPED_PRESSURE_PLATE,
                Blocks.STONE_PRESSURE_PLATE,					Blocks.POLISHED_BLACKSTONE_PRESSURE_PLATE,
                Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE,			Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE,
                
                Blocks.ACACIA_SHELF,							Blocks.BAMBOO_SHELF,
                Blocks.BIRCH_SHELF,								Blocks.CHERRY_SHELF,
                Blocks.DARK_OAK_SHELF,							Blocks.JUNGLE_SHELF,
                Blocks.MANGROVE_SHELF,							Blocks.OAK_SHELF,
                Blocks.PALE_OAK_SHELF,							Blocks.SPRUCE_SHELF,
                Blocks.CRIMSON_SHELF,							Blocks.WARPED_SHELF,
                
                Blocks.RAIL,									Blocks.ACTIVATOR_RAIL,
                Blocks.DETECTOR_RAIL,							Blocks.POWERED_RAIL
            ));
        // @formatter:on

        private Component title;
        private ItemLike icon;
        private BlockConfig cfg;

        Template(String translation, ItemLike icon, BlockConfig cfg) {
            this.title = Component.translatable(translation);
            this.icon = icon;
            this.cfg = cfg;
        }

        @Override
        public Component getTitle() {
            return title;
        }

        @Override
        public ItemLike getIcon() {
            return icon;
        }

        public BlockConfig create() {
            return cfg.clone();
        }

        public BlockConfig create(int color) {
            BlockConfig cfg = create();
            cfg.setColor(color);
            return cfg;
        }

        public void cloneInto(BlockConfig cfg) {
            cfg.cloneInto(this.cfg);
        }

    }

    @Expose
    private SyncedBlockList blocks;

    @Expose
    private ViewMode viewMode;

    public BlockConfig() {
        this(ViewMode.EXCLUSIVE);
    }

    private BlockConfig(BlockConfig other) {
        super(other);
    }

    public BlockConfig(ViewMode viewMode, Block... blocks) {
        super();
        this.viewMode = Objects.requireNonNull(viewMode, "viewMode can't be null!");
        this.blocks = new SyncedBlockList(blocks);
    }

    public BlockConfig(int key, int ScanCode, String name, ViewMode viewMode, Block... blocks) {
        super(key, ScanCode, name);
        this.viewMode = Objects.requireNonNull(viewMode, "viewMode can't be null!");
        this.blocks = new SyncedBlockList(blocks);
    }

    @Override
    public void cloneInto(AbstractModeConfig cfg) {
        if (!(cfg instanceof BlockConfig other))
            throw new IllegalArgumentException("Can't clone config from another type!");

        super.cloneInto(cfg);

        this.viewMode = other.viewMode;
        this.blocks = other.blocks.clone();
    }

    public SyncedBlockList getBlocks() {
        return blocks;
    }

    @Override
    public void shouldSideBeRendered(BlockState currentState,BlockState neighberState, CallbackInfoReturnable<Boolean> ci) {
        if (!isEnabled())
            return;

        String name = Objects.requireNonNullElse(BuiltInRegistries.BLOCK.getKey(currentState.getBlock()), "").toString();
        boolean present = blocks.contains(name);
        boolean shouldRender = viewMode.getViewer().shouldRenderSide(present, currentState, neighberState);
        ci.setReturnValue(shouldRender);
    }

    @Override
    public void setEnabled(boolean enabled) {
        setEnabled(enabled, true);
    }

    public void setEnabled(boolean enable, boolean reloadRenderers) {
        XrayMain mod = XrayMain.getMod();

        if (enable) {
            // disable the previous mode
            BlockConfig old = mod.getConfig().getSelectedBlockMode();
            if (old != null)
                old.setEnabled(false);
        }

        super.setEnabled(enable);

        mod.internalFullbright();

        if (reloadRenderers)
            Minecraft.getInstance().levelRenderer.allChanged();
    }

    public ViewMode getViewMode() {
        return viewMode;
    }

    public void setViewMode(ViewMode viewMode) {
        this.viewMode = viewMode;
    }

    @Override
    public BlockConfig clone() {
        return new BlockConfig(this);
    }

}
