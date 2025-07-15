package org.example.model;

public record Complaint(
        String agency,
        String type,
        String descriptor,
        String additonalDetails,
        Boolean publicView,
        String locationType) {
}
