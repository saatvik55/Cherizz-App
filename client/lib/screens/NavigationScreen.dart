import 'package:flutter/material.dart';
import 'GalleryScreen.dart';
import 'Profile/SettingScreen.dart';
import 'home_screen.dart';
import 'login_screen.dart';
import '../utils/storage_helper.dart';

class NavigationPage extends StatefulWidget {
  final String username; // Pass logged-in user's username

  const NavigationPage({Key? key, required this.username}) : super(key: key);

  @override
  _NavigationPageState createState() => _NavigationPageState();
}

class _NavigationPageState extends State<NavigationPage> {
  int _currentIndex = 0;

  late List<Widget> _pages; // Pages depend on user data

  @override
  void initState() {
    super.initState();
    // Initialize pages with user data
    _pages = [
      HomeScreen(username: widget.username),
      GalleryScreen(userId: widget.username), // Replace with user ID if needed
      SettingsScreen(onLogout: _logout),
    ];
  }

  Future<void> _logout() async {
    await StorageHelper.clearToken(); // Clear the token
    Navigator.pushAndRemoveUntil(
      context,
      MaterialPageRoute(builder: (context) => const LoginScreen()),
          (route) => false,
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: _pages[_currentIndex], // Display the selected page
      bottomNavigationBar: BottomNavigationBar(
        currentIndex: _currentIndex,
        onTap: (index) {
          setState(() {
            _currentIndex = index;
          });
        },
        items: const [
          BottomNavigationBarItem(
            icon: Icon(Icons.home),
            label: 'Home',
          ),
          BottomNavigationBarItem(
            icon: Icon(Icons.photo_library),
            label: 'Gallery',
          ),
          BottomNavigationBarItem(
            icon: Icon(Icons.settings),
            label: 'Settings',
          ),
        ],
      ),
    );
  }
}
