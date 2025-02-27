package fr.atesab.xray.widget;

import java.util.List;
import java.util.stream.Stream;

import com.mojang.blaze3d.vertex.PoseStack;

import fr.atesab.xray.config.ESPConfig;
import fr.atesab.xray.screen.XrayEntityMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

public class EntityConfigWidget extends XrayButton {
    private final ESPConfig cfg;
    private int deltaX;
    private int deltaY;

    public EntityConfigWidget(int x, int y, int width, int height, ESPConfig cfg, Screen menu) {
        this(x, y, width, height, cfg, menu, 0, 0);
    }

    public EntityConfigWidget(int x, int y, int width, int height, ESPConfig cfg, Screen menu, int deltaX,
            int deltaY) {
        super(x, y, width, height, Component.literal(""),
                b -> Minecraft.getInstance().setScreen(new XrayEntityMenu(menu, cfg)));
        this.cfg = cfg;
        this.deltaX = deltaX;
        this.deltaY = deltaY;
    }

    public void setDeltaX(int deltaX) {
        this.deltaX = deltaX;
    }

    public void setDeltaY(int deltaY) {
        this.deltaY = deltaY;
    }

    @Override
    public void renderWidget(PoseStack matrices, int mouseX, int mouseY, float delta) {
        int fit = (width - 2) / 17;

        Stream<ItemStack> stacks = Stream.concat(cfg.getEntities().getIcons(), cfg.getBlockEntities().getIcons());

        List<ItemStack> view = stacks.limit(fit).toList();
        Minecraft client = Minecraft.getInstance();

        int x = getX();
        int y = getY();

        if (mouseX >= x && mouseX <= x + this.width && mouseY >= y && mouseY <= y + this.height) {
            Gui.fill(matrices, x, y, x + width, y + height, 0x33ffaa00);
        } else {
            Gui.fill(matrices, x, y, x + width, y + height, 0x33ffffff);
        }

        int left = x + this.width / 2 - view.size() * 17 / 2;
        int top = y + this.height / 2 - 15 / 2;
        for (ItemStack b : view) {
            client.getItemRenderer().renderGuiItem(new PoseStack(), b, left + deltaX, top + deltaY);
            left += 17;
        }
    }

}
