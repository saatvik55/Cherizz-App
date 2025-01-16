package com.gratitude.gratitude_photodiary.config;

import io.imagekit.sdk.ImageKit;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ImageKitConfig {

    @Bean
    public ImageKit imageKit() {
        ImageKit imageKit = ImageKit.getInstance();
        io.imagekit.sdk.config.Configuration config =
                new io.imagekit.sdk.config.Configuration("PublicKey", "PrivateKey", "UrlEndpoint");
        imageKit.setConfig(config);
        return imageKit;
    }
}
