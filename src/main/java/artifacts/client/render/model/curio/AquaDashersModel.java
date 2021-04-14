package artifacts.client.render.model.curio;

public class AquaDashersModel extends ShoesModel {

    public AquaDashersModel(float delta) {
        super(delta);
        leftLeg.texOffs(0, 16);
        leftLeg.addBox(2 + delta, 0, -2 + 3 + delta * 3 / 2, 0, 12, 4, 0, delta, delta);
        rightLeg.texOffs(16, 16);
        rightLeg.addBox(-2 - delta, 0, -2 + 3 + delta * 3 / 2, 0, 12, 4, 0, delta, delta);
    }
}
