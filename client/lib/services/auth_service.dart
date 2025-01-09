import 'dart:convert';
import 'package:http/http.dart' as http;

class AuthService {
  static const String baseUrl = 'http://172.22.55.55:8080/auth';

  // Login API call
  static Future<String> login(Map<String, dynamic> loginData) async {
    final response = await http.post(
      Uri.parse('$baseUrl/login'),
      headers: {'Content-Type': 'application/json'},
      body: jsonEncode(loginData),
    );

    if (response.statusCode == 200) {
      final data = jsonDecode(response.body);
      if (data['userId'] != null) {
        return data['userId'];
      } else {
        throw Exception('Token not found in response');
      }
    } else {
      final data = jsonDecode(response.body);
      throw Exception(data['error'] ?? 'Login failed');
    }
  }


  // Signup API call
  static Future<void> signup(Map<String, dynamic> signupData ) async {
    final response = await http.post(
      Uri.parse('$baseUrl/signup'),
      headers: {'Content-Type': 'application/json'},
      body: jsonEncode(signupData),
    );

    if (response.statusCode != 200) {
      throw Exception('Signup failed');
    }
  }
}
