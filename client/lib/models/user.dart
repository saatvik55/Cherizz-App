class User {
  final String userId;
  final String email;
  final String firstName;
  final String lastName;
  final String phone;
  final String displayName;
  final bool emailVerified;
  final DateTime createdAt;

  User({
    required this.userId,
    required this.email,
    required this.firstName,
    required this.lastName,
    required this.phone,
    required this.displayName,
    required this.emailVerified,
    required this.createdAt,
  });

  // Factory constructor to create a User from JSON
  factory User.fromJson(Map<String, dynamic> json) {
    return User(
      userId: json['userId'] ?? '',
      email: json['email'] ?? '',
      firstName: json['firstName'] ?? '',
      lastName: json['lastName'] ?? '',
      phone: json['phone'] ?? '',
      displayName: json['displayName'] ?? '',
      emailVerified: json['emailVerified'] ?? false,
      createdAt: DateTime.fromMillisecondsSinceEpoch(json['createdAt']),
    );
  }

  // Convert a User object to JSON
  Map<String, dynamic> toJson() {
    return {
      'userId': userId,
      'email': email,
      'firstName': firstName,
      'lastName': lastName,
      'phone': phone,
      'displayName': displayName,
      'emailVerified': emailVerified,
      'createdAt': createdAt.millisecondsSinceEpoch,
    };
  }
}
