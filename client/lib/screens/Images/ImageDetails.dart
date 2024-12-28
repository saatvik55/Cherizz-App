import 'package:flutter/material.dart';

class ImageDetailsScreen extends StatelessWidget {
  final String imageUrl; // URL or Base64 string of the image

  const ImageDetailsScreen({Key? key, required this.imageUrl}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Image Details'),
      ),
      body: Center(
        child: Column(
          children: [
            const SizedBox(height: 20),
            Image.network(imageUrl), // Replace with Image.memory if using Base64
            const SizedBox(height: 20),
            const Text('Additional metadata goes here'),
          ],
        ),
      ),
    );
  }
}
