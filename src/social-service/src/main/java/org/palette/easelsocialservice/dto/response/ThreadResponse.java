package org.palette.easelsocialservice.dto.response;

import java.util.List;

public record ThreadResponse(
   Long theadId,
   List<PaintResponse> paints

) {
}
