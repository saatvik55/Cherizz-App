import 'dart:convert';
import 'package:flutter_dotenv/flutter_dotenv.dart';
import 'package:http/http.dart' as http;
import '../models/user.dart';
class UserService {
  static final  baseUrl = dotenv.env['BASE_URL'];
  static final String apiBase= '$baseUrl/users';

  Future<User> fetchUser(String userId) async {
    final response = await http.get(Uri.parse('$apiBase/$userId'));

    if (response.statusCode == 200) {
      final data = jsonDecode(response.body);
      return User.fromJson(data);
    } else {
      throw Exception('Failed to fetch user details: ${response.reasonPhrase}');
    }
  }
}
