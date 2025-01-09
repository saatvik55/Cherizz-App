import 'package:flutter/material.dart';
import '../services/image_service.dart';

class GalleryScreen extends StatefulWidget {
  final String userId;

  const GalleryScreen({Key? key, required this.userId}) : super(key: key);

  @override
  _GalleryScreenState createState() => _GalleryScreenState();
}

class _GalleryScreenState extends State<GalleryScreen> {
  final ImageService _imageService = ImageService();
  late Future<List<Map<String, dynamic>>> _images;

  @override
  void initState() {
    super.initState();
    _images = _imageService.fetchImages(widget.userId);
  }

  Future<void> _refreshImages() async {
    setState(() {
      _images = _imageService.fetchImages(widget.userId);
    });
  }

  Future<void> _uploadImage() async {
    // Replace with your image picker logic
    const imageUrl = 'https://example.com/sample-image.jpg';

    try {
      await _imageService.uploadImage(widget.userId, imageUrl);
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(content: Text('Image uploaded successfully!')),
      );
      await _refreshImages(); // Refresh the image list
    } catch (e) {
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text('Failed to upload image: $e')),
      );
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Gallery'),
      ),
      body: FutureBuilder<List<Map<String, dynamic>>>(
        future: _images,
        builder: (context, snapshot) {
          if (snapshot.connectionState == ConnectionState.waiting) {
            return const Center(child: CircularProgressIndicator());
          } else if (snapshot.hasError) {
            return Center(child: Text('Error: ${snapshot.error}'));
          } else if (!snapshot.hasData || snapshot.data!.isEmpty) {
            return const Center(child: Text('No images uploaded yet.'));
          } else {
            final images = snapshot.data!;
            return GridView.builder(
              padding: const EdgeInsets.all(10),
              gridDelegate: const SliverGridDelegateWithFixedCrossAxisCount(
                crossAxisCount: 2,
                crossAxisSpacing: 10,
                mainAxisSpacing: 10,
              ),
              itemCount: images.length,
              itemBuilder: (context, index) {
                final image = images[index];
                return Card(
                  elevation: 5,
                  child: Column(
                    children: [
                      Expanded(
                        child: Image.network(
                          image['imageUrl'],
                          fit: BoxFit.cover,
                        ),
                      ),
                      Padding(
                        padding: const EdgeInsets.all(8.0),
                        child: Text(
                          image['uploadedAt'],
                          style: const TextStyle(fontSize: 12),
                        ),
                      ),
                    ],
                  ),
                );
              },
            );
          }
        },
      ),
      floatingActionButton: FloatingActionButton(
        onPressed: _uploadImage,
        tooltip: 'Upload Image',
        child: const Icon(Icons.add),
      ),
    );
  }
}
