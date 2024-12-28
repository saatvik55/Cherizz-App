import 'package:flutter/material.dart';

class UploadImageScreen extends StatelessWidget {
  const UploadImageScreen({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Upload Image'),
      ),
      body: Center(
        child: const Text(
          'Upload Image Screen',
          style: TextStyle(fontSize: 18),
        ),
      ),
    );
  }
}
