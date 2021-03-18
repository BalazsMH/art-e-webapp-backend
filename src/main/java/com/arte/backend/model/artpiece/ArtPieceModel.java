package com.arte.backend.model.artpiece;

import com.arte.backend.model.apiresponse.WebImage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ArtPieceModel {
    private String longTitle;
    private WebImage webImage;
    private String objectNumber;
}
