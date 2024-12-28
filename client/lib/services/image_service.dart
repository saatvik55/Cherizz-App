import 'dart:convert';
import 'dart:io';
import 'package:http/http.dart' as http;
import 'package:image_picker/image_picker.dart';

class ImageService {
  final String baseUrl = 'http://192.168.1.10:8080/api'; // Replace with your backend URL

  Future<String> getToken() async {
    // Replace with your method to retrieve the JWT token
    return 'your-jwt-token';
  }

  Future<List<String>> fetchImages(String userId) async {
    final response = await http.get(
      Uri.parse('$baseUrl/images?userId=$userId'),
      headers: {
        'Authorization': 'Bearer ${await getToken()}',
      },
    );

    if (response.statusCode == 200) {
      final List<dynamic> data = jsonDecode(response.body);
      return data.map((item) => item['imageUrl'] as String).toList();
    } else {
      throw Exception('Failed to load images');
    }
  }

  Future<void> uploadImage(String userId) async {
    final ImagePicker picker = ImagePicker();
    final XFile? pickedFile = await picker.pickImage(source: ImageSource.gallery);

    if (pickedFile == null) {
      return; // User canceled image selection
    }

    final File imageFile = File(pickedFile.path);

    final request = http.MultipartRequest(
      'POST',
      Uri.parse('$baseUrl/upload'),
    );

    request.headers['Authorization'] = 'Bearer ${await getToken()}';
    request.fields['userId'] = userId;
    request.files.add(await http.MultipartFile.fromPath('image', imageFile.path));

    final response = await request.send();

    if (response.statusCode != 200) {
      throw Exception('Failed to upload image');
    }
  }
}
