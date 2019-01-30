package artifacts.client.model;

import artifacts.Artifacts;
import artifacts.common.ModItems;
import baubles.api.BaubleType;
import baubles.api.BaublesApi;
import baubles.api.cap.IBaublesItemHandler;
import baubles.common.Config;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class GlovesRenderLayer implements LayerRenderer<EntityPlayer> {

    private final RenderPlayer renderPlayer;

    private final ModelPlayer model;

    public final ResourceLocation feralClawsTextures;
    public final ResourceLocation powerGloveTextures;
    public final ResourceLocation mechanicalGloveTextures;
    public final ResourceLocation fireGauntletTextures;
    public final ResourceLocation fireGauntletOverlayTextures;

    public GlovesRenderLayer(boolean smallArms, RenderPlayer renderPlayer) {
        this.renderPlayer = renderPlayer;
        model = new ModelPlayer(0.5F, smallArms);
        model.setVisible(false);
        feralClawsTextures = new ResourceLocation(Artifacts.MODID, "textures/entity/layer/feral_claws_" + (smallArms ? "slim" : "normal") + ".png");
        powerGloveTextures = new ResourceLocation(Artifacts.MODID, "textures/entity/layer/power_glove_" + (smallArms ? "slim" : "normal") + ".png");
        mechanicalGloveTextures = new ResourceLocation(Artifacts.MODID, "textures/entity/layer/mechanical_glove_" + (smallArms ? "slim" : "normal") + ".png");
        fireGauntletTextures = new ResourceLocation(Artifacts.MODID, "textures/entity/layer/fire_gauntlet_" + (smallArms ? "slim" : "normal") + ".png");
        fireGauntletOverlayTextures = new ResourceLocation(Artifacts.MODID, "textures/entity/layer/fire_gauntlet_overlay_" + (smallArms ? "slim" : "normal") + ".png");
    }

    @Override
    public void doRenderLayer(@Nonnull EntityPlayer player, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        if (!Config.renderBaubles || player.getActivePotionEffect(MobEffects.INVISIBILITY) != null) {
            return;
        }

        GlStateManager.enableLighting();
        GlStateManager.enableRescaleNormal();

        float lastLightmapX = OpenGlHelper.lastBrightnessX;
        float lastLightmapY = OpenGlHelper.lastBrightnessY;

        int light = 15728880;
        int lightmapX = light % 65536;
        int lightmapY = light / 65536;

        model.setModelAttributes(renderPlayer.getMainModel());
        model.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, player);

        IBaublesItemHandler baubleHandler = BaublesApi.getBaublesHandler(player);

        ResourceLocation textures = getTextures(baubleHandler.getStackInSlot(BaubleType.RING.getValidSlots()[0]));
        if (textures != null) {
            Minecraft.getMinecraft().getTextureManager().bindTexture(textures);
            model.bipedLeftArm.showModel = true;
            model.bipedLeftArmwear.showModel = true;
            model.render(player, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
            textures = getOverlayTextures(baubleHandler.getStackInSlot(BaubleType.RING.getValidSlots()[0]));
            if (textures != null) {
                Minecraft.getMinecraft().getTextureManager().bindTexture(textures);
                OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lightmapX, lightmapY);
                model.render(player, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
                OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lastLightmapX, lastLightmapY);
            }
            model.bipedLeftArmwear.showModel = false;
            model.bipedLeftArm.showModel = false;
        }
        textures = getTextures(baubleHandler.getStackInSlot(BaubleType.RING.getValidSlots()[1]));
        if (textures != null) {
            Minecraft.getMinecraft().getTextureManager().bindTexture(textures);
            model.bipedRightArm.showModel = true;
            model.bipedRightArmwear.showModel = true;
            model.render(player, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
            textures = getOverlayTextures(baubleHandler.getStackInSlot(BaubleType.RING.getValidSlots()[1]));
            if (textures != null) {
                Minecraft.getMinecraft().getTextureManager().bindTexture(textures);
                OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lightmapX, lightmapY);
                model.render(player, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
                OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lastLightmapX, lastLightmapY);
            }
            model.bipedRightArmwear.showModel = false;
            model.bipedRightArm.showModel = false;
        }
    }

    private @Nullable ResourceLocation getTextures(ItemStack stack) {
        if (stack.getItem() == ModItems.POWER_GLOVE) {
            return powerGloveTextures;
        } else if (stack.getItem() == ModItems.FERAL_CLAWS) {
            return feralClawsTextures;
        } else if (stack.getItem() == ModItems.MECHANICAL_GLOVE) {
            return mechanicalGloveTextures;
        } else if (stack.getItem() == ModItems.FIRE_GAUNTLET) {
            return fireGauntletTextures;
        }
        return null;
    }

    private @Nullable
    ResourceLocation getOverlayTextures(ItemStack stack) {
        if (stack.getItem() == ModItems.FIRE_GAUNTLET) {
            return fireGauntletOverlayTextures;
        }
        return null;
    }

    @Override
    public boolean shouldCombineTextures() {
        return false;
    }
}