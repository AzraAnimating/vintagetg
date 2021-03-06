package at.tyron.vintagecraft.WorldGen;

import at.tyron.vintagecraft.World.BiomeVC;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.WorldChunkManager;

public class WorldTypeVC extends WorldType {
	
	public static WorldTypeVC DEFAULT = new WorldTypeVC(0, "VC Default");
	public static WorldTypeVC FLAT = new WorldTypeVC(1, "VC Flat");
	
	public WorldTypeVC(String name) {
		super(name);
	}

	public WorldTypeVC(int i, String par2Str) {
		super(i, par2Str);
	}	
	
	@Override
	public WorldChunkManager getChunkManager(World world) {
		if (this == FLAT) {
			return new WorldChunkManagerFlatVC(world);
		}
		return new WorldChunkManagerVC(world);
	}
	
	
	@Override
	public int getMinimumSpawnHeight(World world) {
		return 120;
	}
	
	
}
