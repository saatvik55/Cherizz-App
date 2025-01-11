import 'package:client/screens/NavigationScreen.dart';
import 'package:client/screens/login_screen.dart';
import 'package:client/utils/storage_helper.dart';
import 'package:flutter/material.dart';

void main() {
  WidgetsFlutterBinding.ensureInitialized();
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Gratitude Photo Diary',
      theme: ThemeData(primarySwatch: Colors.blue),
      home: FutureBuilder<String?>(
        future: StorageHelper.getUserId(),
        builder: (context, snapshot) {
          if (snapshot.connectionState == ConnectionState.waiting) {
            return CircularProgressIndicator();
          } else if (snapshot.hasData && snapshot.data != null) {
            final userId = snapshot.data!;
            return NavigationPage(
              userId: userId,
            );
          } else {
            return LoginScreen();
          }
        },
      ),
    );
  }
}