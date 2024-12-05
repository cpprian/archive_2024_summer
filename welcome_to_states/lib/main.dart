import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';
import 'package/drawer_template.dart';

void main() => runApp(const MyApp());

class MyApp extends StatelessWidget {
  const MyApp({Key? key}) : super(key: key);

  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Welcome to States!',
      theme: ThemeData(
        appBarTheme: const AppBarTheme(
          backgroundColor: Colors.white,
          foregroundColor: Colors.black,
        ),
      ),
      home: const HomePage(),
    );
  }
}

class HomePage extends StatefulWidget {
  const HomePage({ Key? key }) : super(key: key);

  @override
  State<HomePage> createState() => _HomePageState();
}

class _HomePageState extends State<HomePage> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Welcome to States!'),
      ),
      drawer: const DrawerTemplate(),
      body: Container(
        constraints: const BoxConstraints.expand(),
        decoration: const BoxDecoration(
          image: DecorationImage(
            image: AssetImage('images/home.webp'),
            fit: BoxFit.cover,
          ),
        ),
        child: FittedBox(
            fit: BoxFit.contain,
            child: Text(
              'welcome to states!'.toUpperCase(),
              style: const TextStyle(
                fontSize: 120,
                color: Colors.white,
              ),
            ),
          ),
          ),
    ); // Scaffold
  }
}