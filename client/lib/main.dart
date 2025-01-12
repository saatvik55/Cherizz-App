import 'package:client/screens/NavigationScreen.dart';
import 'package:client/screens/login_screen.dart';
import 'package:client/utils/UserProvider.dart';
import 'package:client/utils/storage_helper.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:flutter_dotenv/flutter_dotenv.dart';

void main() async {
  WidgetsFlutterBinding.ensureInitialized();
  final isLoggedIn = await UserStorage.isLoggedIn();
  final user = isLoggedIn ? await UserStorage.loadUser() : null;
  await dotenv.load(fileName: ".env");
  runApp(
    ChangeNotifierProvider(
      create: (context) => UserManager()..setUser(user),
      child: MyApp(hasLoggedIn: user != null),
    ),
  );
}

class MyApp extends StatelessWidget {
  final bool hasLoggedIn;
  const MyApp({Key? key, required this.hasLoggedIn}) : super(key: key);
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Gratitude Photo Diary',
      theme: ThemeData(primarySwatch: Colors.blue),
      home: hasLoggedIn ? NavigationPage() : LoginScreen(),
    );
  }
}