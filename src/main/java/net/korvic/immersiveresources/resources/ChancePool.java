package net.korvic.immersiveresources.resources;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ChancePool {
	private Map<Material, ItemStack> results;
	private List<Float> chances = new ArrayList<>();

	public void placeRandom(Location origin, ResourceType type) {
	}

	public void placeSpecific(Location origin, ResourceType type, int index) {

	}

}
