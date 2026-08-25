package fr.atesab.xray.screen.page;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.input.CharacterEvent;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.input.MouseButtonEvent;

public class PagedElement<E> implements GuiEventListener {
    PagedScreen<E> parentScreen;
    private boolean focus;
    private final List<AbstractWidget> widgets = new ArrayList<>();
    private final List<GuiEventListener> guiListeners = new ArrayList<>();

    public PagedElement(PagedScreen<E> parent) {
        this.parentScreen = parent;
    }

    public PagedScreen<E> getParentScreen() {
        return parentScreen;
    }

    public E save() {
        return null;
    }

    public <W extends AbstractWidget> W addSubRenderableWidget(W widget) {
        widgets.add(widget);
        return widget;
    }

    public <W extends AbstractWidget> W addSubWidget(W widget) {
        guiListeners.add(widget);
        return addSubRenderableWidget(widget);
    }

    public int getParentHeight() {
        return parentScreen.height;
    }

    public void setup(int deltaY, int index) {
        widgets.clear();
        guiListeners.clear();
        this.init();
        this.updateDelta(deltaY, index);
    }

    public void init() {
    }

    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float delta) {
        widgets.forEach(w -> w.extractRenderState(graphics, mouseX, mouseY, delta));
    }

    public void tick() {
    }

    @Override
    public boolean charTyped(CharacterEvent event) {
        for (GuiEventListener w : guiListeners)
            if (w.charTyped(event))
                return true;
        return false;
    }

    @Override
    public boolean keyReleased(KeyEvent event) {
        for (GuiEventListener w : guiListeners)
            if (w.keyReleased(event))
                return true;
        return false;
    }

    @Override
    public boolean mouseClicked(MouseButtonEvent event, boolean doubleClick) {
        for (GuiEventListener w : guiListeners)
            if (w.mouseClicked(event, doubleClick))
                return true;
        return false;
    }

    @Override
    public boolean mouseDragged(MouseButtonEvent event, double endMouseX, double endMouseY) {
        for (GuiEventListener w : guiListeners)
            if (w.mouseDragged(event, endMouseX, endMouseY))
                return true;
        return false;
    }

    @Override
    public boolean mouseReleased(MouseButtonEvent event) {
        for (GuiEventListener w : guiListeners)
            if (w.mouseReleased(event))
                return true;
        return false;
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollV, double scrollH) {
        for (GuiEventListener w : guiListeners)
            if (w.mouseScrolled(mouseX, mouseY, scrollV, scrollH))
                return true;
        return false;
    }

    @Override
    public void mouseMoved(double mouseX, double mouseY) {
        guiListeners.forEach(w -> w.mouseMoved(mouseX, mouseY));
    }

    @Override
    public boolean isMouseOver(double mouseX, double mouseY) {
        for (GuiEventListener guiEventListener : guiListeners)
            if (guiEventListener.isMouseOver(mouseX, mouseY))
                return true;
        return false;
    }

    @Override
    public void setFocused(boolean focus) {
        this.focus = focus;
    }

    @Override
    public boolean isFocused() {
        return focus;
    }

    @Override
    public boolean keyPressed(KeyEvent event) {
        for (GuiEventListener w : guiListeners)
            if (w.keyPressed(event))
                return true;
        return false;
    }

    public void updateDelta(int delta, int index) {
    }

}
