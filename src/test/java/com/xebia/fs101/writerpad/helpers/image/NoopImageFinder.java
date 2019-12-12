package com.xebia.fs101.writerpad.helpers.image;

import com.xebia.fs101.writerpad.services.helpers.image.ImageFinder;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("test")
public class NoopImageFinder implements ImageFinder {
    @Override
    public String findRandomImage() {
        return "";
    }
}
