import 'package:flutter/material.dart';
import '../main.dart';
import '../welcome_to_states/distance_page.dart';
import '../welcome_to_states/temperature_page.dart';
import '../welcome_to_states/weight_page.dart';
import '../welcome_to_states/currency_page.dart';

class DrawerTemplate extends StatelessWidget {
  const DrawerTemplate({ Key? key }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Drawer(
        child: ListView(
          padding: EdgeInsets.zero,
          children: <Widget>[
            const DrawerHeader(
              child: Text(
                'What would you like to convert? 🤔',
                style: TextStyle(
                    fontSize: 24,
                    color: Colors.black,
                  ),
                ),
            ),
            ListTile(
              title: const Text('Home'),
              trailing: const Icon(Icons.home),
              onTap: () {
                Navigator.push(
                    context,
                    MaterialPageRoute(
                      builder: (context) => const HomePage(),
                    ),
                  );
              },
            ),
            ListTile(
              title: const Text('Temperature'),
              trailing: const Icon(Icons.thermostat),
              onTap: () {
                Navigator.push(
                  context,
                  MaterialPageRoute(
                    builder: (context) => const TemperaturePage(),
                  ),
                );
              },
            ),
            ListTile(
              title: const Text('Distance'),
              trailing: const Icon(Icons.directions_car),
              onTap: () {
                Navigator.push(
                  context,
                  MaterialPageRoute(
                    builder: (context) => const DistancePage(),
                  ),
                );
              }
            ),
            ListTile(
              title: const Text('Weight'),
              trailing: const Icon(Icons.line_weight),
              onTap: () {
                Navigator.push(
                  context, 
                  MaterialPageRoute(
                    builder: (context) => const WeightPage(),
                    ),
                );
              }
            ),
            ListTile(
              title: const Text('Currency'),
              trailing: const Icon(Icons.attach_money),
              onTap: () {
                Navigator.push(
                  context,
                  MaterialPageRoute(
                    builder: (context) => const CurrencyPage(),
                  ),
                );
              },
            ),
          ],
        ));
  }
} 