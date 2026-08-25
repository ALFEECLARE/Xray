package fr.atesab.xray.widget;

import org.joml.Matrix3x2fStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.input.InputWithModifiers;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

public class MenuWidget extends AbstractButton {
    public interface OnPress {
        void onPress();
    }

    private final ItemStack itemStack;
    private final OnPress onPress;

    public MenuWidget(int x, int y, int w, int h, Component text, ItemStack stack, OnPress onPress) {
        super(x, y, w, h, text);
        this.onPress = onPress;
        this.itemStack = stack;
    }

    @Override
    public void extractContents(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float delta) {
        Minecraft client = Minecraft.getInstance();
        boolean hovered = isHoveredOrFocused();
        int color;
        if (hovered) {
            color = 0x33ffffff;
        } else {
            color = 0x22ffffff;
        }

        graphics.fill(getX(), getY(), getX() + width, getY() + height, color);

        Component message = getMessage();
        Font font = client.font;
        
        Matrix3x2fStack modelstack = graphics.pose();
        modelstack.pushMatrix();
        float scaleX = getWidth() * 3 / 4f / 16f;
        float scaleY = getHeight() * 3 / 4f / 16f;
        float stackOffsetedX = (width - 16 * scaleX) / 2;
        float stackOffsetedY = (height - 16 * scaleY) / 2;
        modelstack.scale(scaleX, scaleY);
        graphics.fakeItem(itemStack, (int)((getX() + stackOffsetedX) / scaleX), (int)((getY() + stackOffsetedY) / scaleY));
        modelstack.scale(1 / scaleX, 1 / scaleY);
        modelstack.popMatrix();
        
        Matrix3x2fStack stack = graphics.pose();
        stack.pushMatrix();
        float scale = getHeight() / 7f / font.lineHeight;
        int offsetX = (int)((width - font.width(message.getVisualOrderText())) / scale / 2);
        stack.translate(getX() + offsetX, getY() + getHeight());
        stack.scale(scale, scale);
        graphics.text(font, message, 0, -font.lineHeight, packedFGColor);
        stack.scale(1 / scale, 1 / scale);
        stack.translate(-getY() - offsetX, -getY() - getHeight());
        stack.popMatrix();
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput elementOutput) {
        this.defaultButtonNarrationText(elementOutput);
    }


    @Override
    public void onPress(InputWithModifiers input) {
        onPress.onPress();
    }

}
