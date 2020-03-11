package net.korvic.immersiveresources.resources.quarry;

import co.lotc.core.bukkit.util.Run;
import com.sk89q.worldedit.math.BlockVector2;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import net.korvic.immersiveresources.ImmersiveResources;
import net.korvic.immersiveresources.resources.ChancePool;
import net.korvic.immersiveresources.resources.ResourceType;
import net.korvic.immersiveresources.resources.ResourcesList;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;

public class Quarry extends ResourcesList {

	private File file; // YamlConfig of this Quarry's section in the config.
	private World world; // World this Quarry is located in.
	private String name; // Name of the Quarry.
	private ChancePool chancePool; // Chance and Pool of blocks for this Quarry.
	private ProtectedRegion region; // The WG region of this Quarry.
	private int refillValue; // Refill Value for this Quarry.
	private int cooldown; // Cooldown for this Quarry.
	private long lastUse; // Last time this Quarry was refilled.

	private Quarry childQuarry;

	private Quarry(File file, String name, World world, ChancePool chancePool, ProtectedRegion region, int refillValue, int cooldown, long lastUse) {
		this.file = file;
		this.name = name;
		this.world = world;
		this.chancePool = chancePool;
		this.region = region;
		this.refillValue = refillValue;
		this.cooldown = cooldown;
		this.lastUse = lastUse;
	}

	//// GET/SET ////

	public String getName() {
		return name;
	}
	public ChancePool getChancePool() {
		return chancePool;
	}
	public ResourceType getType() {
		return ResourceType.QUARRY;
	}

	public ArrayList<Location> getLocationList() {
		ArrayList<Location> locations = new ArrayList<>();

		List<BlockVector2> points = region.getPoints();
		int minY = region.getMinimumPoint().getY();
		int maxY = region.getMaximumPoint().getY();

		for (BlockVector2 point : points) {
			for (int i = minY; i <= maxY; i++) {
				locations.add(new Location(world, point.getBlockX(), i, point.getBlockZ()));
			}
		}

		return locations;
	}


	//// COMPARE AND SAVE ////

	@Override
	public String toString() {
		return "ResourcePit{" +
			   "name='" + name + '\'' +
			   ", world=" + world +
			   ", pool=" + chancePool +
			   ", region=" + region +
			   ", refillValue=" + refillValue +
			   ", cooldown=" + cooldown +
			   ", lastUse=" + lastUse +
			   '}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Quarry that = (Quarry) o;
		return (name.equals(that.name) &&
				Objects.equals(world, that.world) &&
				Objects.equals(chancePool, that.chancePool) &&
				Objects.equals(region, that.region) &&
				Objects.equals(refillValue, that.refillValue) &&
				Objects.equals(cooldown, that.cooldown) &&
				Objects.equals(lastUse, that.lastUse));
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, world, chancePool, region, refillValue, cooldown, lastUse);
	}

	// Runs a save task for this specific resource pit
	public void save() {
		// Queue a task using a Tythan Util
		Run.as(ImmersiveResources.get()).async(() -> {
			YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);

			try {
				// Set our values, don't worry if they exist or not as long as we aren't throwing an NPE
				configuration.set("regionName", region == null ? null : region.getId());
				configuration.set("worldName", world == null ? null : world.getName());
				configuration.set("childQuarry", childQuarry);
				configuration.set("cooldown", cooldown);
				configuration.set("lastUse", lastUse);
				configuration.set("refillValue", refillValue);
				configuration.set("chancePool", chancePool);

				configuration.save(file);
			} catch (IOException e) {
				ImmersiveResources.get().getLogger().log(Level.SEVERE, "Failed to save Quarry.", e);
			}
		});
	}


	//// BUILDER ////

	public static Builder builder(String name, File file) {
		return new Builder(name, file);
	}

	// Quarry Builder Class
	public static class Builder {
		private final String name;
		private final File file;
		private World world;
		private ChancePool pool;
		private ProtectedRegion region;
		private Integer refillValue;
		private Integer cooldown;
		private long lastUse;

		private Builder(String name, File file) {
			if (name == null) {
				throw new IllegalArgumentException("Name cannot be null");
			}
			this.name = name.toUpperCase();
			this.file = file;
		}

		public Builder world(World world) {
			this.world = world;
			return this;
		}

		public Builder chancepool(ChancePool pool) {
			this.pool = pool;
			return this;
		}

		public Builder region(ProtectedRegion region) {
			this.region = region;
			return this;
		}

		public Builder refillValue(Integer refillValue) {
			this.refillValue = refillValue;
			return this;
		}

		public Builder cooldown(Integer cooldown) {
			this.cooldown = cooldown;
			return this;
		}

		public Builder lastUse(Long lastUse) {
			this.lastUse = lastUse;
			return this;
		}

		public Quarry build() {
			return new Quarry(file, name, world, pool, region, refillValue == null ? 0 : refillValue, cooldown == null ? 0 : cooldown, lastUse);
		}
	}

}
