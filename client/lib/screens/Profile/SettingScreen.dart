import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import '../../utils/UserProvider.dart';

class SettingsScreen extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Settings'),
        actions: [
          IconButton(
            icon: const Icon(Icons.logout),
            onPressed: () {
              Provider.of<UserManager>(context, listen: false).logout(context);
            },
          ),
        ],
      ),
      body: Center(
        child: const Text('Settings Page'),
      ),
    );
  }
}