package com.ssafy.voyage.util;

import org.springframework.stereotype.Component;

@Component
public class ImageValidator {
    public boolean validateImage(String image) {


        return image != null && image.startsWith("data:image/png;base64,");
    }
}
