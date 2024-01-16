package org.palette.easeluserservice.persistence.embed;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Pin {

    public List<Long> loadAll(String value) {
        return Arrays.stream(value.split(","))
                .map(Long::valueOf)
                .collect(Collectors.toList());
    }

    public Map<Long, Boolean> isContained(String value, List<Long> dmIdList) {
        String[] strArr = value.split(",");
        Map<Long, Boolean> resultMap = new HashMap<>();

        dmIdList.forEach(
                dmId -> resultMap.put(
                        dmId, Arrays.asList(strArr).contains(dmId.toString())
                )
        );

        return resultMap;
    }
}
