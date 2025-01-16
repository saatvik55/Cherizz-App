import 'dart:convert';
import 'package:flutter_dotenv/flutter_dotenv.dart';
import 'package:http/http.dart' as http;

class ImageKitService {
  final String imageKitUrl = "https://upload.imagekit.io/api/v1/files/upload";
  final String backendUrl = dotenv.env['BASE_URL']!;
  final  privateKey = dotenv.env["PRIVATE_KEY"]; // Replace with your actual private key

  /// Upload an image to ImageKit
  Future<String> uploadImage(String base64Image, String fileName) async {
    try {
      final response = await http.post(
        Uri.parse('$backendUrl/imagekit/upload'), // Backend endpoint
        headers: {
          'Content-Type': 'application/json',
        },
        body: json.encode({
          'file': base64Image,
          'fileName': fileName,
        }),
      );

      if (response.statusCode == 200) {
        final responseBody = json.decode(response.body);
        return responseBody['url']; // Return the uploaded image URL
      } else {
        throw Exception(
            'Failed to upload image to backend: ${response.statusCode} - ${response.reasonPhrase}');
      }
    } catch (e) {
      throw Exception('Error uploading image to backend: $e');
    }
  }
}