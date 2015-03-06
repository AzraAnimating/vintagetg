package at.tyron.vintagecraft.WorldProperties;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import at.tyron.vintagecraft.VCraftWorld;
import at.tyron.vintagecraft.World.BiomeVC;

public enum EnumCrustLayer {
	TOPSOIL (-1, 1, 0),
	SUBSOIL (-1, 1, 0),
	REGOLITH(-1, 2, 1),
	ROCK_1   (0, -1, -1),
	ROCK_2   (1, -1, -1),
	ROCK_3   (2, -1, -1),
	;
	
	public static final int quantityFixedTopLayers = 3;
	
	public final int dataLayerIndex;
	public final int thickness;
	public final int underwaterThickness;
	
	private EnumCrustLayer(int index, int depthStart, int underwaterDepthStart) {
		this.dataLayerIndex = index;
		this.thickness = depthStart;
		this.underwaterThickness = underwaterDepthStart;
	}
	
	
	public static EnumCrustLayer fromDataLayerIndex(int datalayerindex) {
		for (int i = 0; i < values().length; i++) {
			if (values()[i].dataLayerIndex == datalayerindex) return values()[i];
		}
		return null;
	}
	
	
	public IBlockState getFixedBlock(EnumRockType rocktype, int x, int y, int z, int depth, int steepness) {
		switch (this) { 
			case TOPSOIL: return VCraftWorld.instance.getTopLayerAtPos(x, y, z, rocktype, steepness); 
			case SUBSOIL: return VCraftWorld.instance.getSubLayerAtPos(x, y, z, rocktype, steepness);
			//case REGOLITH: if (steepness < 1) return rocktype.getRockVariantForBlock();
			default: return null;
		}
	}
	

	
	public static EnumCrustLayer crustLayerForDepth(int targetdepth, int[][]rockdata, int arrayIndexChunk, boolean underwater) {
		int depth = 0, i = 0;
		
		for (EnumCrustLayer layer : EnumCrustLayer.values()) {
			if (layer.thickness == -1) {
				depth += (rockdata[i - quantityFixedTopLayers][arrayIndexChunk] >> 16) & 0xff;
			} else {
				depth += underwater ? layer.underwaterThickness : layer.thickness;
			}
			
			if (targetdepth < depth) return layer;
			i++;
		}
		
		return ROCK_3;
	}
	
	
}