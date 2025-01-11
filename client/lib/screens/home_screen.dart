import 'package:flutter/material.dart';
import '../utils/storage_helper.dart';
import 'GalleryScreen.dart';
import 'login_screen.dart';

class HomeScreen extends StatelessWidget {
  final String userId;
  const HomeScreen({
    Key? key,
    required this.userId,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {

    return Scaffold(
      appBar: AppBar(
        title: const Text('Home'),
        actions: [
          IconButton(
            icon: const Icon(Icons.logout), onPressed: () {  },

          ),
        ],
      ),
      body: Center(
        child: ElevatedButton(
          onPressed: () async {
            final userId = await StorageHelper.getUserId();
            if (userId != null ) {
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