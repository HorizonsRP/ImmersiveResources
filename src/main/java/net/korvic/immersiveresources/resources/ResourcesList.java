package net.korvic.immersiveresources.resources;

import net.korvic.immersiveresources.resources.node.Node;
import net.korvic.immersiveresources.resources.quarry.Quarry;

import java.util.ArrayList;
import java.util.List;

public class ResourcesList {

	//// STATIC ////

	// QUARRIES //

	protected static List<Quarry> allQuarryList = new ArrayList<>();
	public static List<Quarry> getQuarryList() {
		return allQuarryList;
	}

	public static Quarry getPit(String name) {
		Quarry output = null;
		if (name != null) {
			for (Quarry quarry : allQuarryList) {
				if (quarry.getName().equalsIgnoreCase(name)) {
					output = quarry;
					break;
				}
			}
		}
		return output;
	}


	// NODES //

	protected static List<Node> allNodeList = new ArrayList<>();
	public static List<Node> getNodeList() {
		return allNodeList;
	}
	private static int currentID = 0;

	public static Node getNode(int id) {
		Node output = null;
		for (Node node : allNodeList) {
			if (node.getNodeID() == id) {
				output = node;
				break;
			}
		}
		return output;
	}

}
