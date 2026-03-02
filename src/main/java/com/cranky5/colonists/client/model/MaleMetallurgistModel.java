package com.cranky5.colonists.client.model;

import com.minecolonies.api.client.render.modeltype.CitizenModel;
import com.minecolonies.api.entity.citizen.AbstractEntityCitizen;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import org.jetbrains.annotations.NotNull;

/**
 * Male Metallurgist citizen model.
 * <p>
 * Features a protective visor that flips down over the eyes while the
 * citizen is actively working (welding pose), and rests on the forehead
 * when idle.
 * <p>
 * Texture sheet: 128×64.
 */
public class MaleMetallurgistModel extends CitizenModel<AbstractEntityCitizen> {

    public MaleMetallurgistModel(final ModelPart root) {
        super(root);
        hat.visible = false;
    }

    public static LayerDefinition createMesh() {
        MeshDefinition mesh = HumanoidModel.createMesh(CubeDeformation.NONE, 0.0F);
        PartDefinition root = mesh.getRoot();

        // ── Head ─────────────────────────────────────────────
        PartDefinition head = root.addOrReplaceChild("head",
                CubeListBuilder.create()
                        .texOffs(0, 0)
                        .addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, CubeDeformation.NONE)
                        .texOffs(32, 0)
                        .addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, new CubeDeformation(0.5F)),
                PartPose.ZERO);

        // Visor – resting position (on forehead, up)
        head.addOrReplaceChild("visorUp",
                CubeListBuilder.create()
                        .texOffs(96, 16)
                        .addBox(-4.0F, -9.5F, -4.6F, 8, 2, 1, new CubeDeformation(0.1F))
                        .texOffs(88, 10)
                        .addBox(-4.5F, -9.0F, -4.0F, 0, 1, 8, new CubeDeformation(0.05F))
                        .texOffs(104, 10)
                        .addBox(4.5F, -9.0F, -4.0F, 0, 1, 8, new CubeDeformation(0.05F)),
                PartPose.ZERO);

        // Visor – working position (over eyes)
        head.addOrReplaceChild("visorDown",
                CubeListBuilder.create()
                        .texOffs(96, 6)
                        .addBox(-4.0F, -5.5F, -4.6F, 8, 3, 1, new CubeDeformation(0.1F))
                        .texOffs(88, 0)
                        .addBox(-4.5F, -5.0F, -4.0F, 0, 1, 8, new CubeDeformation(0.05F))
                        .texOffs(104, 0)
                        .addBox(4.5F, -5.0F, -4.0F, 0, 1, 8, new CubeDeformation(0.05F)),
                PartPose.ZERO);

        return LayerDefinition.create(mesh, 128, 64);
    }

    @Override
    public void setupAnim(@NotNull final AbstractEntityCitizen entity,
                          float limbSwing, float limbSwingAmount,
                          float ageInTicks, float netHeadYaw, float headPitch) {
        super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        final boolean working = isWorking(entity);
        head.getChild("visorDown").visible = working;
        head.getChild("visorUp").visible   = !working;
    }
}
