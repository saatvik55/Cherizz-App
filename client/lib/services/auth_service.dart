import 'dart:convert';
import 'package:http/http.dart' as http;

class AuthService {
  static const String baseUrl = 'http://172.22.55.55:8080/auth';

  // Login API call
  static Future<String> login(Map<String, String> credentials) async {
    final response = await http.post(
      Uri.parse('$baseUrl/login'),
      headers: {'Content-Type': 'application/json'},
      body: jsonEncode(credentials),
    );

    if (response.statusCode == 200) {
      final data = jsonDecode(response.body);
      final String userId = data['userId'];
      return userId;
    } else {
      throw Exception('Failed to login');
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

  static Future<Map<String, dynamic>> getUserById(String userId) async {
    final response = await http.get(
      Uri.parse('$baseUrl/user/$userId'),
      headers: {'Authorization': 'Bearer <your-token>'},
    );

    if (response.statusCode == 200) {
      return jsonDecode(response.body);
    } else {
      throw Exception('Failed to fetch user details');
    }
  }

}
