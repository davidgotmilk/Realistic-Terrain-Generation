package rtg.world.gen.terrain;

import rtg.api.world.RTGWorld;

public class TerrainGrasslandFlats extends TerrainBase {

    public TerrainGrasslandFlats() {

    }

    @Override
    public float generateNoise(RTGWorld rtgWorld, int x, int y, float border, float river) {

        return terrainGrasslandFlats(x, y, rtgWorld.simplex, river, 40f, 25f, 68f);
    }
}
