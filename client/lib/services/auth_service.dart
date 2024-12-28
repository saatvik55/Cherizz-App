import 'dart:convert';
import 'package:http/http.dart' as http;
import '../utils/storage_helper.dart';

class AuthService {
  static const String baseUrl = 'http://192.168.1.10:8080/auth';

  // Login API call
  static Future<String> login(String username, String password) async {
    final response = await http.post(
      Uri.parse('http://192.168.1.10:8080/auth/login'),
      headers: {'Content-Type': 'application/json'},
      body: jsonEncode({'username': username, 'password': password}),
    );

    if (response.statusCode == 200) {
      final data = jsonDecode(response.body);
      if (data['token'] != null) {
        return data['token'];
      } else {
        throw Exception('Token not found in response');
      }
    } else {
      final data = jsonDecode(response.body);
      throw Exception(data['error'] ?? 'Login failed');
    }
  }


  // Signup API call
  static Future<void> signup(String username, String password) async {
    final response = await http.post(
      Uri.parse('$baseUrl/signup'),
      headers: {'Content-Type': 'application/json'},
      body: jsonEncode({'username': username, 'password': password}),
    );

    if (response.statusCode != 200) {
      throw Exception('Signup failed');
    }
  }
}
