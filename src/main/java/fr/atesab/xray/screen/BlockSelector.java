package fr.atesab.xray.screen;

import java.util.ArrayList;
import java.util.List;

import fr.atesab.xray.utils.GuiUtils;
import fr.atesab.xray.widget.XrayButton;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.CharacterEvent;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

public abstract class BlockSelector extends XrayScreen {
    private final List<Block> blocks;
    private final List<Block> visible = new ArrayList<>();
    private EditBox searchBar;
    private XrayButton nextPage;
    private XrayButton lastPage;
    private int elementByPage = 1;
    private int elementsX = 1;
    private int elementsY = 1;
    private int page = 0;

    public BlockSelector(Screen parent) {
        super(Component.translatable("x13.mod.menu.selector"), parent);
        blocks = new ArrayList<>();
        BuiltInRegistries.BLOCK.forEach(blocks::add);
    }

    @Override
    protected void init() {
        int sizeX = Math.min(width, 400);
        int sizeY = Math.min(height - 48, 400);

        elementsX = sizeX / 18;
        elementsY = sizeY / 18;
        elementByPage = elementsX * elementsY;

        int pageTop = height / 2 - sizeY / 2 - 24;
        int pageBottom = height / 2 + sizeY / 2 + 2;

        searchBar = new EditBox(font, width / 2 - sizeX / 2, pageTop + 2, sizeX, 16, Component.literal("")) {
            @Override
            public boolean mouseClicked(MouseButtonEvent event, boolean doubleClicked) {
                if (event.button() == 1 && event.x() >= this.getX() && event.x() <= this.getX() + this.width && event.y() >= this.getY()
                        && event.y() <= this.getY() + this.height) {
                    setValue("");
                    return true;
                }
                return super.mouseClicked(event, doubleClicked);
            }

            @Override
            public void setValue(String text) {
                super.setValue(text);
                updateSearch();
            }

            @Override
            public boolean keyPressed(KeyEvent event) {
                if (super.keyPressed(event)) {
                    updateSearch();
                    return true;
                }
                return false;
            }

            @Override
            public boolean charTyped(CharacterEvent event) {
                if (super.charTyped(event)) {
                    updateSearch();
                    return true;
                }
                return false;
            }
        };

        lastPage = new XrayButton(width / 2 - 124, pageBottom, 20, 20, Component.literal("<-"), b -> {
            page--;
            updateArrows();
        });
        XrayButton cancelBtn = new XrayButton(width / 2 - 100, pageBottom, 200, 20, Component.translatable("gui.cancel"),
                b -> {
                    getMinecraft().setScreen(parent);
                });
        nextPage = new XrayButton(width / 2 + 104, pageBottom, 20, 20, Component.literal("->"), b -> {
            page++;
            updateArrows();
        });

        addWidget(searchBar);
        addRenderableWidget(lastPage);
        addRenderableWidget(cancelBtn);
        addRenderableWidget(nextPage);

        updateArrows();
        updateSearch();

        setFocused(searchBar);
    }

    public void updateArrows() {
        nextPage.active = (page + 1) * elementByPage < visible.size(); // have last page
        lastPage.active = page * elementByPage > 0; // have next page
    }

    public void updateSearch() {
        String query = searchBar.getValue().toString().toLowerCase();
        visible.clear();
        blocks.stream().filter(block -> I18n.get(block.getDescriptionId()).toLowerCase().contains(query))
                .forEach(visible::add);
        page = Math.min(visible.size() / elementByPage, page);
        updateArrows();
    }

    public List<Block> getView() {
        return visible.subList(page * elementByPage, Math.min((page + 1) * elementByPage, visible.size()));
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
    	//extractBackground(graphics, mouseX, mouseY, partialTick);
        searchBar.extractRenderState(graphics, mouseX, mouseY, partialTick);
        int left = width / 2 - elementsX * 18 / 2;
        int top = height / 2 - elementsY * 18 / 2;

        List<Block> view = getView();
        Block hoveredBlock = null;
        int i;
        for (i = 0; i < view.size(); i++) {
            Block b = view.get(i);
            int x = left + (i % elementsX) * 18;
            int y = top + (i / elementsX) * 18;

            int color;
            ItemStack stack = new ItemStack(b);

            if (hoveredBlock == null && mouseX >= x && mouseX <= x + 18 && mouseY >= y && mouseY <= y + 18) {
                color = 0x4466ffff;
                hoveredBlock = b;
            } else {
                color = 0x44666699;
            }

            graphics.fill(x, y, x + 18, y + 18, color);
            GuiUtils.renderItemIdentity(graphics, stack, x + 1, y + 1);
        }
        super.extractRenderState(graphics, mouseX, mouseY, partialTick);

        if (hoveredBlock != null) {
            graphics.setTooltipForNextFrame(font, Component.translatable(hoveredBlock.getDescriptionId()), mouseX, mouseY);
        }
    }

    @Override
    public boolean mouseClicked(MouseButtonEvent event, boolean doubleClick) {
        if (super.mouseClicked(event, doubleClick))
            return true;

        int left = width / 2 - elementsX * 18 / 2;
        int top = height / 2 - elementsY * 18 / 2;

        List<Block> view = getView();
        int i;
        for (i = 0; i < view.size(); i++) {
            Block b = view.get(i);
            int x = left + (i % elementsX) * 18;
            int y = top + (i / elementsX) * 18;
            if (event.x() >= x && event.x() <= x + 18 && event.y() >= y && event.y() <= y + 18) {
                if (event.button() == 0) { // left click: select
                    save(b);
                    getMinecraft().setScreen(parent);
                    return true;
                }
                return false;
            }
        }
        return false;
    }

    /**
     * save the selected block (only call when a Block is selected, doesn't call
     * after a cancel)
     * 
     * @param selection the selected block
     */
    protected abstract void save(Block selection);
}
