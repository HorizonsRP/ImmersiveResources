package net.korvic.immersiveresources.resources.node;

import net.korvic.immersiveresources.resources.ChancePool;
import net.korvic.immersiveresources.resources.ResourceType;
import net.korvic.immersiveresources.resources.ResourcesList;
import org.bukkit.Location;

public class Node extends ResourcesList {

	private int nodeID;
	private int refreshTime;
	private Location origin;
	private ChancePool chancePool;
	private ResourceType type;

	private int lastRefreshed;

	public Node(ResourceType type, int nodeID, int refreshTime, Location origin, ChancePool chancePool) {
		this.type = type;
		this.nodeID = nodeID;
		this.refreshTime = refreshTime;
		this.origin = origin;
		this.chancePool = chancePool;
	}

	// Get Info //
	public ResourceType getType() {
		return type;
	}
	public int getNodeID() {
		return nodeID;
	}
	public int getRefreshTime() {
		return refreshTime;
	}
	public Location getOrigin() {
		return origin;
	}

	public boolean isBlock() {
		return type.getSuperiorType() == 1;
	}
	public boolean isItem() {
		return type.getSuperiorType() == -1;
	}
	public boolean isRefreshable() {
		return (System.currentTimeMillis() - lastRefreshed) >= refreshTime;
	}

	// Operations //
	public void refreshRandom(boolean forced) {
		if (forced || isRefreshable()) {
			chancePool.placeRandom(origin, type);
		}
	}

	public void refreshIndex(boolean forced, int index) {
		if (forced || isRefreshable()) {
			chancePool.placeSpecific(origin, type, index);
		}
	}
}
