import 'package:flutter/material.dart';
import '../utils/storage_helper.dart';
import 'GalleryScreen.dart';
import 'login_screen.dart';

class HomeScreen extends StatelessWidget {
  const HomeScreen({Key? key, required String username}) : super(key: key);

  Future<void> _logout(BuildContext context) async {
    await StorageHelper.clearUserDetails();
    Navigator.pushAndRemoveUntil(
      context,
      MaterialPageRoute(builder: (context) => const LoginScreen()),
          (route) => false,
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Home'),
        actions: [
          IconButton(
            icon: const Icon(Icons.logout),
            onPressed: () => _logout(context),
          ),
        ],
      ),
      body: Center(
        child: ElevatedButton(
          onPressed: () async {
            final userId = await StorageHelper.getUserId();
            final username = await StorageHelper.getUsername();
            if (userId != null && username != null) {
              Navigator.push(
                context,
                MaterialPageRoute(
                  builder: (context) => GalleryScreen(userId: userId),
                ),
              );
            } else {
              ScaffoldMessenger.of(context).showSnackBar(
                const SnackBar(content: Text('User details missing. Please log in again.')),
              );
            }
          },
          child: const Text('View My Gallery'),
        ),
      ),
    );
  }
}
