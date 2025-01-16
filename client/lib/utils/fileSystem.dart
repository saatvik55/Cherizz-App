import 'dart:convert';
import 'dart:io';
import 'package:flutter/foundation.dart';
import 'package:image_picker/image_picker.dart'; // For kIsWeb

Future<String> convertToBase64(XFile pickedImage) async {
  try {
    // For web
    if (kIsWeb) {
      print("Running on Web");
      final bytes = await pickedImage.readAsBytes();
      print("Web: Image converted to bytes");
      return base64Encode(bytes);
    }

    // For mobile (iOS/Android)
    else {
      print("Running on Mobile");
      final bytes = await File(pickedImage.path).readAsBytes();
      print("Mobile: Image converted to bytes");
      return base64Encode(bytes);
    }
  } catch (e) {
    throw Exception('Error converting image to Base64: $e');
  }
}
