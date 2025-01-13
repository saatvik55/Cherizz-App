import 'dart:convert';
import 'package:flutter_dotenv/flutter_dotenv.dart';
import 'package:http/http.dart' as http;

class ImageService {
  static final String baseUrl = dotenv.env['BASE_URL']!;
  static final String apiBase = '$baseUrl/users';

  /// Fetch images for the user using their userId
  Future<List<Map<String, dynamic>>> fetchImages(String userId) async {
    final response = await http.get(
      Uri.parse('$apiBase/$userId/images'), // Use a RESTful endpoint
      headers: {
        'Content-Type': 'application/json',
      },
    );

    if (response.statusCode == 200) {
      return List<Map<String, dynamic>>.from(json.decode(response.body));
    } else {
      throw Exception('Failed to fetch images: ${response.reasonPhrase}');
    }
  }

  /// Upload an image for the user using their userId
  Future<String> uploadImage(String userId, String imageUrl) async {
    final response = await http.post(
      Uri.parse('$apiBase/$userId/images'), // RESTful endpoint for uploading
      headers: {
        'Content-Type': 'application/json',
      },
      body: json.encode({
        'imageUrl': imageUrl, // Only pass the imageUrl
      }),
    );

    if (response.statusCode == 200) {
      final responseBody = json.decode(response.body);
      return responseBody['imageId']; // Return the generated imageId
    } else {
      throw Exception('Failed to upload image: ${response.reasonPhrase}');
    }
  }

  /// Delete an image for the user using their userId and imageId
  Future<void> deleteImage(String userId, String imageId) async {
    final response = await http.delete(
      Uri.parse('$apiBase/$userId/images/$imageId'), // RESTful endpoint for deletion
      headers: {
        'Content-Type': 'application/json',
      },
    );

    if (response.statusCode != 200) {
      throw Exception('Failed to delete image: ${response.reasonPhrase}');
    }
  }
}
