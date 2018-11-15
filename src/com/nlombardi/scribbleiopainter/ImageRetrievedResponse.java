package com.nlombardi.scribbleiopainter;

import java.util.List;

public interface ImageRetrievedResponse {
    void error(String message);

    void completed(List<ImageResult> images);
}
