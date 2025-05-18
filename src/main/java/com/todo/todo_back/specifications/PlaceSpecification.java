package com.todo.todo_back.specifications;

import org.springframework.data.jpa.domain.Specification;

public class PlaceSpecification {

    private PlaceSpecification() {
        throw new IllegalStateException("Utility class");
    }

    public static Specification<Place> latitudeGreaterThan(Double value) {
        return (root, query, builder) -> builder.greaterThanOrEqualTo(root.get(Place.Fields.LATITUDE.getDatabaseFieldName()), value);
    }

    public static Specification<Place> latitudeLessThan(Double value) {
        return (root, query, builder) -> builder.lessThanOrEqualTo(root.get(Place.Fields.LATITUDE.getDatabaseFieldName()), value);
    }

    public static Specification<Place> longitudeGreaterThan(Double value) {
        return (root, query, builder) -> builder.greaterThanOrEqualTo(root.get(Place.Fields.LONGITUDE.getDatabaseFieldName()), value);
    }

    public static Specification<Place> longitudeLessThan(Double value) {
        return (root, query, builder) -> builder.lessThanOrEqualTo(root.get(Place.Fields.LONGITUDE.getDatabaseFieldName()), value);
    }

}
