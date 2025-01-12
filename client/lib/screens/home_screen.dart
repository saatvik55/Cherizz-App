import 'package:client/utils/UserProvider.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'GalleryScreen.dart';

class HomeScreen extends StatelessWidget {
  const HomeScreen({
    Key? key,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Home'),
        actions: [
          IconButton(
            icon: const Icon(Icons.logout),
            onPressed: () {
              UserManager().logout(context);
            },
          ),
        ],
      ),
      body: Center(
        child: ElevatedButton(
          onPressed: () async {
            final user = Provider.of<UserManager>(context, listen: false).user;
            // print(user?.email);
            if (user!= null) {
              Navigator.push(
                context,
                MaterialPageRoute(
                  builder: (context) => GalleryScreen(),
                ),
              );
            } else {
              ScaffoldMessenger.of(context).showSnackBar(
                const SnackBar(
                    content:
                        Text('User details missing. Please log in again.')),
              );
            }
          },
          child: const Text('View My Gallery'),
        ),
      ),
    );
  }

}
