package fr.atesab.xray.screen;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.joml.Matrix3x2fStack;

import fr.atesab.xray.XrayMain;
import fr.atesab.xray.config.BlockConfig;
import fr.atesab.xray.config.ESPConfig;
import fr.atesab.xray.widget.MenuWidget;
import fr.atesab.xray.widget.XrayButton;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.fml.ModContainer;

public class XrayMenu extends XrayScreen {

    public XrayMenu(ModContainer conainer, Screen parent) {
        super(Component.translatable("x13.mod.config"), parent);
    }

    @Override
    protected void init() {
        int size = 400 / 5;
        int x = width / 2 - 200;
        int i = 0;

        XrayMain mod = XrayMain.getMod();

        addRenderableWidget(
                new XrayButton(width / 2 - 100, height / 2 + 52, 200, 20, Component.translatable("gui.done"),
                        btn ->
                                minecraft.setScreen(parent)
                ));

        addRenderableWidget(new MenuWidget(x + size * i++, height / 2 - size / 2, size, size,
                Component.translatable("x13.mod.mode"), new ItemStack(Blocks.DIAMOND_ORE), () ->
                minecraft.setScreen(new XrayBlockModesConfig(this, mod.getConfig().getBlockConfigs().stream()) {
                    @Override
                    protected void save(List<BlockConfig> list) {
                        mod.getConfig().setBlockConfigs(list);
                        mod.saveConfigs();
                    }
                })
        ));
        addRenderableWidget(new MenuWidget(x + size * i++, height / 2 - size / 2, size, size,
                Component.translatable("x13.mod.esp"), new ItemStack(Blocks.CREEPER_HEAD), () ->
                minecraft.setScreen(new XrayESPModesConfig(this, mod.getConfig().getEspConfigs().stream()) {
                    @Override
                    protected void save(List<ESPConfig> list) {
                        mod.getConfig().setEspConfigs(list);
                        mod.saveConfigs();
                    }
                })
        ));
        addRenderableWidget(new MenuWidget(x + size * i++, height / 2 - size / 2, size, size,
                Component.translatable("x13.mod.fullbright"), new ItemStack(Blocks.GLOWSTONE), () ->
                mod.fullBright()
        ));
        addRenderableWidget(new MenuWidget(x + size * i++, height / 2 - size / 2, size, size,
                Component.translatable("x13.mod.showloc"), new ItemStack(Items.PAPER), () ->
                minecraft.setScreen(new XrayLocationConfig(this) {
                    @Override
                    protected void save() {
                        mod.saveConfigs();
                    }
                })
        ));
        addRenderableWidget(new MenuWidget(x + size * i++, height / 2 - size / 2, size, size,
                Component.translatable("x13.mod.config"), new ItemStack(Items.REDSTONE), () ->
                minecraft.setScreen(new XrayConfigMenu(this))
        ));

        super.init();
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float delta) {
    	//super.extractBackground(graphics, mouseX, mouseY, delta);
        Matrix3x2fStack stack = graphics.pose();
        stack.pushMatrix();
        stack.translate(width / 2f, height / 2f - 70);
        stack.scale(4, 4);
        graphics.centeredText(minecraft.font, XrayMain.MOD_NAME, 0, -minecraft.font.lineHeight, 0xffffff33);
        stack.popMatrix();
        Component component =  Component.translatable("x13.mod.by", Arrays.stream(XrayMain.MOD_AUTHORS).collect(Collectors.joining(", ")));
        graphics.text(minecraft.font, component, (width - font.width(component.getVisualOrderText())) / 2, height / 2 - 60, 0xffaaaaaa);
        int size = 400 / 5;
        graphics.fill(0, height / 2 - size / 2, width / 2 - 200, height / 2 + size / 2, 0x22ffffff);
        graphics.fill(width / 2 + 200, height / 2 - size / 2, width, height / 2 + size / 2, 0x22ffffff);

        super.extractRenderState(graphics, mouseX, mouseY, delta);
    }
}
