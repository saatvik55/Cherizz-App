import 'package:flutter/material.dart';
import '../models/user.dart';

class UserProvider with ChangeNotifier {
  User? _user;

  User? get user => _user;

  void setUser(User user) {
    _user = user;
    notifyListeners();
  }

  void clearUser() {
    _user = null;
    notifyListeners();
  }
}

class UserManager with ChangeNotifier {
  static final UserManager _instance = UserManager._internal();
  User? _user;

  UserManager._internal();

  factory UserManager() {
    return _instance;
  }

  User? get user => _user;

  void setUser(User user) {
    _user = user;
    notifyListeners(); // Notify listeners of the change
  }

  void clearUser() {
    _user = null;
    notifyListeners(); // Notify listeners of the change
  }
}

