package fr.atesab.xray.screen;

import java.util.Optional;
import java.util.function.Consumer;

import org.lwjgl.glfw.GLFW;

import fr.atesab.xray.utils.KeyData;
import fr.atesab.xray.widget.XrayButton;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public class KeySelector extends XrayScreen {
    private static final MutableComponent NONE_KEY = Component.translatable("x13.mod.selector.key.none");

    private final Consumer<Optional<KeyData>> keyConsumer;
    private Optional<KeyData> value;
    private boolean isWaitingKey = false;

    private XrayButton cancelButton;
    private XrayButton keyButton;
    private XrayButton doneButton;

    public KeySelector(Screen parent, KeyData key, Consumer<Optional<KeyData>> keyConsumer) {
        this(parent, Optional.of(key), keyConsumer);
    }

    public KeySelector(Screen parent, Consumer<Optional<KeyData>> keyConsumer) {
        this(parent, Optional.empty(), keyConsumer);
    }

    public KeySelector(Screen parent, Optional<KeyData> currentValue, Consumer<Optional<KeyData>> keyConsumer) {
        super(Component.translatable("x13.mod.selector.key.title"), parent);
        value = currentValue;
        this.keyConsumer = keyConsumer;
    }

    private void waitKey() {
        isWaitingKey = true;
        cancelButton.active = false;
        doneButton.active = false;
        keyButton.active = keyButton.visible = false;
    }

    private void setKey(Optional<KeyData> value) {
        this.value = value;
        keyButton.setMessage(KeyData.getName(value));

        cancelButton.active = true;
        doneButton.active = true;
        keyButton.active = keyButton.visible = true;

        isWaitingKey = false;
    }

    @Override
    protected void init() {
        keyButton = addRenderableWidget(new XrayButton(width / 2 - 100, height / 2 - 24, 200, 20,
                NONE_KEY, b -> waitKey()));
        doneButton = addRenderableWidget(new XrayButton(width / 2 - 100, height / 2, 200, 20,
                Component.translatable("gui.done"), b -> {
                    keyConsumer.accept(value);
                    minecraft.setScreen(parent);
                }));
        cancelButton = addRenderableWidget(new XrayButton(width / 2 - 100, height / 2 + 24, 200, 20,
                Component.translatable("gui.cancel"), b -> {
                    minecraft.setScreen(parent);
                }));
        setKey(value);
        super.init();
    }

    @Override
    public boolean keyReleased(KeyEvent event) {
    	int key = event.key();
        if (isWaitingKey) {
            if (key == GLFW.GLFW_KEY_ESCAPE) {
                setKey(Optional.empty());
                return true;
            }

            if (!(
                    key == GLFW.GLFW_KEY_LEFT_CONTROL || key == GLFW.GLFW_KEY_RIGHT_CONTROL ||
                            key == GLFW.GLFW_KEY_LEFT_ALT || key == GLFW.GLFW_KEY_RIGHT_ALT ||
                            key == GLFW.GLFW_KEY_LEFT_SHIFT || key == GLFW.GLFW_KEY_RIGHT_SHIFT
            )) {
                boolean alt = event.hasAltDown();
                boolean ctrl = event.hasControlDown();
                boolean shift = event.hasShiftDown();
                setKey(Optional.of(new KeyData(event.key(), event.scancode(), alt, ctrl, shift)));
                return true;
            }
        }

        return super.keyPressed(event);
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return !isWaitingKey;
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float delta) {
    	//extractBackground(graphics, mouseX, mouseY, delta);
        graphics.centeredText(font, getTitle(), width / 2, height / 2 - 30 - font.lineHeight, 0xffffffff);

        if (isWaitingKey) {
            graphics.centeredText(font, Component.translatable("x13.mod.selector.key.presskey"), width / 2,
                    keyButton.getY() + keyButton.getHeight() / 2 - font.lineHeight, 0xffffff00);
        }

        super.extractRenderState(graphics, mouseX, mouseY, delta);
    }

}
