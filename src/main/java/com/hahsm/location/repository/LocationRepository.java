package com.hahsm.location.repository;

import java.util.Optional;
import java.util.function.Predicate;

import com.hahsm.common.type.Observer;
import com.hahsm.common.type.Repository;
import com.hahsm.datastructure.ArrayList;
import com.hahsm.datastructure.HashMap;
import com.hahsm.datastructure.adt.List;
import com.hahsm.datastructure.adt.Map;
import com.hahsm.location.model.Location;

public class LocationRepository implements Repository<Location, Integer> {
    private final Map<Integer, Location> locations = new HashMap<>();
    private final Map<String, Double> cityDistances = new HashMap<>();

    public LocationRepository() {
        // Adding five sample cities
        locations.put(1, new Location(1, "Hanoi"));
        locations.put(2, new Location(2, "Haiphong"));
        locations.put(3, new Location(3, "Bac Ninh"));
        locations.put(4, new Location(4, "Hai Duong"));
        locations.put(5, new Location(5, "Vinh Phuc"));

        // Storing distances between city pairs (in km)
        cityDistances.put("Hanoi-Haiphong", 120.0);
        cityDistances.put("Hanoi-Bac Ninh", 35.0);
        cityDistances.put("Hanoi-Hai Duong", 57.0);
        cityDistances.put("Hanoi-Vinh Phuc", 75.0);
        cityDistances.put("Haiphong-Bac Ninh", 85.0);
        cityDistances.put("Bac Ninh-Hai Duong", 40.0);
        // Add more distances as needed
    }

    @Override
    public List<Location> getAll() {
        return new ArrayList<>(locations.values());
    }

    @Override
    public List<Location> getByFilter(final Predicate<Location> filter) {
        final List<Location> result = new ArrayList<>();
        for (final Location location : locations.values()) {
            if (filter.test(location)) {
                result.add(location);
            }
        }
        return result;
    }

    @Override
    public Optional<Location> getByID(final Integer id) {
        return Optional.ofNullable(locations.get(id));
    }

    @Override
    public boolean update(final Location entity) {
        return false; // Not allowed for hardcoded data
    }

    @Override
    public Location insert(final Location newEntity) {
        return null; // Not allowed for hardcoded data
    }

    @Override
    public List<Location> insert(final List<Location> entities) {
        return null; // Not allowed for hardcoded data
    }

    @Override
    public boolean delete(final Location entity) {
        return false; // Not allowed for hardcoded data
    }

    @Override
    public boolean deleteByID(final Integer id) {
        return false; // Not allowed for hardcoded data
    }

    // Method to get distance between two cities
    public Optional<Double> getDistance(final String city1, final String city2) {
        String key = city1 + "-" + city2;
        if (cityDistances.containsKey(key)) {
            return Optional.of(cityDistances.get(key));
        }
        // Check for reverse order
        key = city2 + "-" + city1;
        return Optional.ofNullable(cityDistances.get(key));
    }

    // Method to get all distances
    public List<String> getAllDistances() {
        final List<String> distances = new ArrayList<>();
        for (final Map.Entry<String, Double> entry : cityDistances.entries()) {
            distances.add(entry.getKey() + ": " + entry.getValue() + " km");
        }
        return distances;
    }

	@Override
	public void registerObserver(final Observer<Location> observer) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'registerObserver'");
	}

	@Override
	public void removeObserver(final Observer<Location> observer) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'removeObserver'");
	}

	@Override
	public void notifyObservers(final Location data) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'notifyObservers'");
	}
}
