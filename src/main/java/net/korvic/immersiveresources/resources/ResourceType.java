package net.korvic.immersiveresources.resources;

public enum ResourceType {
	QUARRY((short) 0),
	NODE_BLOCK((short) 1),
	//NODE_MULTIBLOCK((short) 1),
	NODE_BUSH((short) -1),
	NODE_HANGING((short) -1),
	NODE_RESTING((short) -1);

	short superiorType; // -1 item, 0 quarry, 1 block

	ResourceType(short superiorType) {
		this.superiorType = superiorType;
	}

	public int getSuperiorType() {
		return superiorType;
	}
}
