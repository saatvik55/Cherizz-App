import 'dart:convert';
import 'package:http/http.dart' as http;

class ImageService {
  final String baseUrl = 'http://172.22.55.55:8080/images';

  /// Fetch images for the user using their UID
  Future<List<Map<String, dynamic>>> fetchImages(String uid) async {
    final response = await http.get(
      Uri.parse('$baseUrl?uid=$uid'), // Directly pass the UID as query parameter
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

  /// Upload an image for the user using their UID
  Future<void> uploadImage(String uid, String imageUrl) async {
    final response = await http.post(
      Uri.parse('$baseUrl/upload'),
      headers: {
        'Content-Type': 'application/json',
      },
      body: json.encode({'uid': uid, 'imageUrl': imageUrl}), // Pass UID directly in the body
    );

    if (response.statusCode != 200) {
      throw Exception('Failed to upload image: ${response.reasonPhrase}');
    }
  }
}
