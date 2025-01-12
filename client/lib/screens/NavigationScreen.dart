import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import '../utils/UserProvider.dart';
import 'GalleryScreen.dart';
import 'Profile/SettingScreen.dart';
import 'home_screen.dart';

class NavigationPage extends StatefulWidget {
  const NavigationPage({
    Key? key,
  }) : super(key: key);

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
      HomeScreen(),
      GalleryScreen(), // Replace with user ID if needed
      SettingsScreen()
    ];
  }

  @override
  Widget build(BuildContext context) {
    final user =Provider.of<UserManager>(context).user;
    if(user==null)
      print("User is null");
    else
      print("User is not null ${user.displayName}");
    return Scaffold(
      appBar: AppBar(
        title: const Text('Navigation Page'),
        actions: [
          IconButton(
            icon: const Icon(Icons.logout),
            onPressed: () {
              UserManager().logout(context);
            },
          ),
        ],
      ),
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
