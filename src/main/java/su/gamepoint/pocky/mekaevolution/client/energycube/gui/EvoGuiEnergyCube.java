package su.gamepoint.pocky.mekaevolution.client.energycube.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import mekanism.client.SpecialColors;
import mekanism.client.gui.GuiConfigurableTile;
import mekanism.client.gui.element.GuiSideHolder;
import mekanism.client.gui.element.gauge.GaugeType;
import mekanism.client.gui.element.gauge.GuiEnergyGauge;
import mekanism.client.gui.element.tab.GuiEnergyTab;
import mekanism.client.gui.element.tab.GuiSecurityTab;
import mekanism.common.MekanismLang;
import mekanism.common.inventory.container.tile.MekanismTileContainer;
import mekanism.common.util.text.EnergyDisplay;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import su.gamepoint.pocky.mekaevolution.common.block.storages.energycube.ECTier;
import su.gamepoint.pocky.mekaevolution.common.block.storages.energycube.EvoTileEntityEnergyCube;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * @author Dudko Roman
 */
public class EvoGuiEnergyCube extends GuiConfigurableTile<EvoTileEntityEnergyCube, MekanismTileContainer<EvoTileEntityEnergyCube>> {

    public EvoGuiEnergyCube(MekanismTileContainer<EvoTileEntityEnergyCube> container, Inventory inv, Component title) {
        super(container, inv, title);
        dynamicSlots = true;
    }

    @Override
    protected void addSecurityTab() {
        //Shift if upwards so the armor holder can fit
        addRenderableWidget(new GuiSecurityTab(this, tile, 6));
    }

    @Override
    protected void addGuiElements() {
        //Add the side holder before the slots, as it holds a couple of the slots
        addRenderableWidget(GuiSideHolder.create(this, imageWidth, 36, 98, false, true, SpecialColors.TAB_ARMOR_SLOTS));
        super.addGuiElements();
        addRenderableWidget(new GuiEnergyGauge(tile.getEnergyContainer(), GaugeType.WIDE, this, 55, 18));
        addRenderableWidget(new GuiEnergyTab(this, () -> List.of(MekanismLang.MATRIX_INPUT_RATE.translate(EnergyDisplay.of(tile.getInputRate())),
                MekanismLang.MAX_OUTPUT.translate(EnergyDisplay.of(ECTier.getOutput(tile.getTier()))))));
    }

    @Override
    protected void drawForegroundText(@Nonnull PoseStack matrix, int mouseX, int mouseY) {
        renderTitleText(matrix);
        drawString(matrix, playerInventoryTitle, inventoryLabelX, inventoryLabelY, titleTextColor());
        super.drawForegroundText(matrix, mouseX, mouseY);
    }
}
