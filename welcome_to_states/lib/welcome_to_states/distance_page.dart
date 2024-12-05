import 'package:flutter/material.dart';
import '../package/text_field_template.dart';
import '../package/drawer_template.dart';

class DistancePage extends StatefulWidget {
  const DistancePage({ Key? key }) : super(key: key);

  @override
  State<DistancePage> createState() => _DistancePageState();
}

class _DistancePageState extends State<DistancePage> {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Distance converter',
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
            title: const Text('Distance converter'),
            bottom: const TabBar(
              tabs: <Widget>[
                Tab(text: 'Miles'),
                Tab(text: 'Feets'),
                Tab(text: 'Inches'),
              ],
            ),
          ),
          drawer: const DrawerTemplate(),
          body: const TabBarView(
            children: <Widget>[
              Center (
                child: MilesPage(),
              ),
              Center(
                child: FeetsPage(),
              ),
              Center(
                child: InchesPage(),
              ),
            ],
          ),
          ),
        ),
    );
  }
}

class MilesPage extends StatefulWidget {
  const MilesPage({ Key? key }) : super(key: key);

  @override
  State<MilesPage> createState() => _MilesPageState();
}

class _MilesPageState extends State<MilesPage> {
  final TextEditingController milesController = TextEditingController();
  final TextEditingController kilometersController = TextEditingController();

  List<TextFieldTemplate> textFields = []; 
  
  @override
  void initState() {
   textFields = [
      TextFieldTemplate(
        title: 'Miles',
        controller: milesController,
        factor: 1.609344,
      ),
      TextFieldTemplate(
        title: 'Kilometers',
        controller: kilometersController,
        factor: 0.6213712,
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
              'miles to kilometers'.toUpperCase(), 
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
                          textFields[1].controller.text = (double.parse(textFields[0].controller.text) * textFields[0].factor).toStringAsFixed(2);
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

class FeetsPage extends StatefulWidget {
  const FeetsPage({ Key? key }) : super(key: key);

  @override
  State<FeetsPage> createState() => _FeetsPageState();
}

class _FeetsPageState extends State<FeetsPage> {
  final TextEditingController feetsController = TextEditingController();
  final TextEditingController metersController = TextEditingController();

  List<TextFieldTemplate> textFields = []; 
  
  @override
  void initState() {
   textFields = [
      TextFieldTemplate(
        title: 'Feets',
        controller: feetsController,
        factor: 0.3048,
      ),
      TextFieldTemplate(
        title: 'Meters',
        controller: metersController,
        factor: 3.28084,
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
              'feets to meters'.toUpperCase(), 
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
                          textFields[1].controller.text = (double.parse(textFields[0].controller.text) * textFields[0].factor).toStringAsFixed(2);
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

class InchesPage extends StatefulWidget {
  const InchesPage({ Key? key }) : super(key: key);

  @override
  State<InchesPage> createState() => _InchesPageState();
}

class _InchesPageState extends State<InchesPage> {
  final TextEditingController inchesController = TextEditingController();
  final TextEditingController centimetersController = TextEditingController();

  List<TextFieldTemplate> textFields = []; 
  
  @override
  void initState() {
   textFields = [
      TextFieldTemplate(
        title: 'Inches',
        controller: inchesController,
        factor: 2.54000002489,
      ),
      TextFieldTemplate(
        title: 'Centimeters',
        controller: centimetersController,
        factor: 0.39370079125945,
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
              'inches to centimeters'.toUpperCase(), 
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
                          textFields[1].controller.text = (double.parse(textFields[0].controller.text) * textFields[0].factor).toStringAsFixed(2);
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