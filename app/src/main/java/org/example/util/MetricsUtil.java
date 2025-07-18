package org.example.util;

import org.example.model.Complaint;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Comparator;
import java.lang.Integer;
import java.util.stream.Stream;
import java.util.stream.Collectors;

public class MetricsUtil {
    public static List<String> all(List<Complaint> complaints) {
        List<String> metrics = new ArrayList<String>();

        Stream.of(frequency(complaints))
                .map(HashMap::entrySet)
                .flatMap(Set::stream)
                .max(Comparator.comparing(Map.Entry::getValue))
                .ifPresent(entry -> {
                    String template = "Most common complaint %s with a count of %s";
                    String metric = String.format(template, entry.getKey(), entry.getValue());

                    metrics.add(metric);
                });

        return metrics;
    }

    private static HashMap<String, Integer> frequency(List<Complaint> complaints) {
        var frequencyMap = new HashMap<String, Integer>();

        complaints.stream()
                .map(Complaint::type)
                .forEach(type -> {
                    if (frequencyMap.containsKey(type)) {
                        var count = frequencyMap.get(type);
                        frequencyMap.put(type, count + 1);
                    } else {
                        frequencyMap.put(type, 0);
                    }
                });

        return frequencyMap;
    }
}
