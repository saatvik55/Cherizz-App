import 'dart:convert';
import 'package:http/http.dart' as http;
import '../utils/storage_helper.dart';

class ImageService {
  final String baseUrl = 'http:// 172.22.55.55:8080/images';

  Future<List<Map<String, dynamic>>> fetchImages(String userId) async {
    final token = await StorageHelper.getToken();

    final response = await http.get(
      Uri.parse('$baseUrl?userId=$userId'),
      headers: {
        'Authorization': 'Bearer $token',
        'Content-Type': 'application/json',
      },
    );

    if (response.statusCode == 200) {
      return List<Map<String, dynamic>>.from(json.decode(response.body));
    } else {
      throw Exception('Failed to fetch images: ${response.reasonPhrase}');
    }
  }

  Future<void> uploadImage(String userId, String imageUrl) async {
    final token = await StorageHelper.getToken();

    final response = await http.post(
      Uri.parse('$baseUrl/upload'),
      headers: {
        'Authorization': 'Bearer $token',
        'Content-Type': 'application/json',
      },
      body: json.encode({'userId': userId, 'imageUrl': imageUrl}),
    );

    if (response.statusCode != 200) {
      throw Exception('Failed to upload image: ${response.reasonPhrase}');
    }
  }
}
