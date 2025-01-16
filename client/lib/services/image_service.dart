import 'dart:convert';
import 'package:flutter_dotenv/flutter_dotenv.dart';
import 'package:http/http.dart' as http;
import 'imagekit_service.dart';

class ImageService {
  static final String baseUrl = dotenv.env['BASE_URL'] ?? ''; // Ensure it's not null
  static final String apiBase = '$baseUrl/users';

  /// Fetch images for the user using their userId
  Future<List<Map<String, dynamic>>> fetchImages(String userId) async {
    try {
      final response = await http.get(
        Uri.parse('$apiBase/$userId/images'), // Use a RESTful endpoint
        headers: {
          'Content-Type': 'application/json',
        },
      );

      if (response.statusCode == 200) {
        return List<Map<String, dynamic>>.from(json.decode(response.body));
      } else {
        throw Exception(
            'Failed to fetch images: ${response.statusCode} - ${response.reasonPhrase}');
      }
    } catch (e) {
      throw Exception('Error fetching images: $e');
    }
  }

  /// Upload an image for the user using their userId
  Future<String> uploadImage(String userId, String base64Image) async {
    try {
      print("Uploading to ImageKit...");
      final imageUrl = await ImageKitService().uploadImage(base64Image, 'uploaded_image_${DateTime.now().millisecondsSinceEpoch}');
      print("Image uploaded to ImageKit: $imageUrl");
      print(userId);
      final response = await http.post(
        Uri.parse('$apiBase/$userId/images'), // Replace with your backend endpoint
        headers: {
          'Content-Type': 'application/json',
        },
        body: json.encode({
          'imageUrl': imageUrl, // Send the ImageKit URL
        }),
      );

      if (response.statusCode == 200) {
        final responseBody = json.decode(response.body);
        return responseBody['imageId']; // Return the generated imageId from your backend
      } else {
        throw Exception(
            'Failed to upload image metadata: ${response.statusCode} - ${response.reasonPhrase}');
      }
    } catch (e) {
      throw Exception('Error uploading image: $e');
    }
  }

  /// Delete an image for the user using their userId and imageId
  Future<void> deleteImage(String userId, String imageId) async {
    try {
      final response = await http.delete(
        Uri.parse('$apiBase/$userId/images/$imageId'), // RESTful endpoint for deletion
        headers: {
          'Content-Type': 'application/json',
        },
      );

      if (response.statusCode != 200) {
        throw Exception(
            'Failed to delete image: ${response.statusCode} - ${response.reasonPhrase}');
      }
    } catch (e) {
      throw Exception('Error deleting image: $e');
    }
  }
}
