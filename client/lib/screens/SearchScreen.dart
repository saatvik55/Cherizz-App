import 'package:flutter/material.dart';

class SearchScreen extends StatelessWidget {
  const SearchScreen({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Search Images'),
      ),
      body: Center(
        child: const Text(
          'Search Screen',
          style: TextStyle(fontSize: 18),
        ),
      ),
    );
  }
}
