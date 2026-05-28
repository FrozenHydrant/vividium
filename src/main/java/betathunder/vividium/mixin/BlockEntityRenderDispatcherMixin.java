package betathunder.vividium.mixin;

import betathunder.vividium.Vividium;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.client.model.data.ModelData;
import org.spongepowered.asm.mixin.*;

@Mixin(BlockEntityRenderDispatcher.class)
public class BlockEntityRenderDispatcherMixin {

    @Unique
    @Final
    private final static Minecraft vividium$INSTANCE = Minecraft.getInstance();

    /**
     * @author BetaThunder
     * @reason To violently change the rendering scheme
     */
    @Overwrite
    private static <T extends BlockEntity> void setupAndRender(
            BlockEntityRenderer<T> renderer, T blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource
    ) {

        BlockPos blockPos = blockEntity.getBlockPos();
        double dist = vividium$INSTANCE.gameRenderer.getMainCamera().getPosition().distanceToSqr(blockPos.getCenter());

        int c = 15728880;

        if (dist < 16) {
            renderer.render(blockEntity, partialTick, poseStack, bufferSource, c, OverlayTexture.NO_OVERLAY);
            return;
        }

        if (dist < 32) {
            BlockRenderDispatcher blockRendDisp = vividium$INSTANCE.getBlockRenderer();
            blockRendDisp.renderSingleBlock(Blocks.GLASS.defaultBlockState(), poseStack, bufferSource, 0, 0, ModelData.EMPTY, (RenderType) null);
        }
    }
}
