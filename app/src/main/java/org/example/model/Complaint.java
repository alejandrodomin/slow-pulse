package org.example.model;

public record Complaint(
        String agency,
        String type,
        String descriptor,
        String additonalDetails,
        String publicView,
        String locationType) {
}
