package org.palette.easelsocialservice.dto.response;

import java.util.List;

public record ThreadResponse(
   Integer theadId,
   List<PaintResponse> paints
) {
}
