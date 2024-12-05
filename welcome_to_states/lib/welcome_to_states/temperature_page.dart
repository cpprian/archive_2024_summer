import 'package:flutter/material.dart';
import '../package/text_field_template.dart';
import '../package/drawer_template.dart';

class TemperaturePage extends StatefulWidget {
  const TemperaturePage({ Key? key }) : super(key: key);

  @override
  State<TemperaturePage> createState() => _TemperaturePageState();
}

class _TemperaturePageState extends State<TemperaturePage> {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Temperature converter',
      theme: ThemeData(
        appBarTheme: const AppBarTheme(
          backgroundColor: Colors.white,
          foregroundColor: Colors.black,
        ),
        tabBarTheme: const TabBarTheme(
          labelColor: Colors.black,
          unselectedLabelColor: Colors.grey,
        ),
      ),
      home:
      DefaultTabController(
        initialIndex: 0,
        length: 3,
        child: Scaffold(
          appBar: AppBar(
            title: const Text('Temperature converter'),
          ),
          drawer: const DrawerTemplate(),
          body: FahrenheitPage(),
          ),
        ),
    );
  }
}

class FahrenheitPage extends StatefulWidget {
  const FahrenheitPage({ Key? key }) : super(key: key);

  @override
  State<FahrenheitPage> createState() => _FahrenheitPageState();
}

class _FahrenheitPageState extends State<FahrenheitPage> {
  final TextEditingController fahrenheitController = TextEditingController();
  final TextEditingController celsiusController = TextEditingController();

  List<TextFieldTemplate> textFields = []; 
  
  @override
  void initState() {
   textFields = [
      TextFieldTemplate(
        title: 'Fahrenheit',
        controller: fahrenheitController,
        factor: 1.8,
      ),
      TextFieldTemplate(
        title: 'Celcius',
        controller: celsiusController,
        factor: 1.8,
      ),
    ];
  } 

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Stack(
        children: <Widget>[
          Container(
            alignment: Alignment.topCenter,
            margin: const EdgeInsets.only(top: 100),
            child: Text(
              'fahrenheit to celcius'.toUpperCase(), 
              style: const TextStyle(
                  fontSize: 50, 
                  fontWeight: FontWeight.bold
                ),
              ),
          ),
          Align(
            alignment: Alignment.center,
            child: Column(
              mainAxisSize: MainAxisSize.min,
              children: <Widget>[
                textFields[0],
                textFields[1],
                Row(
                  mainAxisAlignment: MainAxisAlignment.center,
                  children: <Widget>[
                    MaterialButton(
                      child: const Text('convert', style: TextStyle(
                        fontSize: 20,
                        fontWeight: FontWeight.bold,
                      )),
                      onPressed: () {
                        setState(() {
                          if (textFields[0].title == 'Fahrenheit') {
                            textFields[1].controller.text = 
                              ((double.parse(textFields[0].controller.text) - 32) / textFields[0].factor).toStringAsFixed(2);
                          } else {
                            textFields[1].controller.text = 
                              (double.parse(textFields[1].controller.text) * textFields[0].factor + 32).toStringAsFixed(2);
                          }
                        });
                      },
                    ),
                    MaterialButton(
                      child: const Text('swap units', style: TextStyle(
                        fontSize: 20,
                        fontWeight: FontWeight.bold,
                      )),
                      onPressed: () {
                        setState(() {
                          TextFieldTemplate temp = textFields[0];
                          textFields[0] = textFields[1];
                          textFields[1] = temp;
                        });
                      },
                    ),
                  ],
                ),
              ],
            ),
          ),
        ],
      ),
    );
  }
}