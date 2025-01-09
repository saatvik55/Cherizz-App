package com.gratitude.gratitude_photodiary.repository;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.gratitude.gratitude_photodiary.entity.Image;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Repository
public class ImageRepository {

    private final Firestore firestore;

    public ImageRepository(Firestore firestore) {
        this.firestore = firestore;
    }

    private CollectionReference getImagesCollection() {
        return firestore.collection("images");
    }

    public void saveImage(Image image) throws ExecutionException, InterruptedException {
        ApiFuture<WriteResult> future = getImagesCollection().document().set(image);
        future.get(); // Wait for operation to complete
    }

    public List<Image> getImagesByUserId(String userId) throws ExecutionException, InterruptedException {
        Query query = getImagesCollection().whereEqualTo("userId", userId);
        ApiFuture<QuerySnapshot> future = query.get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();

        List<Image> images = new ArrayList<>();
        for (QueryDocumentSnapshot doc : documents) {
            images.add(doc.toObject(Image.class));
        }
        return images;
    }

    public void deleteImage(String imageId) throws ExecutionException, InterruptedException {
        ApiFuture<WriteResult> future = getImagesCollection().document(imageId).delete();
        future.get(); // Wait for operation to complete
    }
}
