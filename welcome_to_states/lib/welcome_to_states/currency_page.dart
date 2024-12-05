import 'package:flutter/material.dart';
import '../package/text_field_template.dart';
import '../package/drawer_template.dart';


class CurrencyPage extends StatefulWidget {
  const CurrencyPage({ Key? key }) : super(key: key);

  @override
  State<CurrencyPage> createState() => _CurrencyPageState();
}

class _CurrencyPageState extends State<CurrencyPage> {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Currency converter',
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
            title: const Text('Currency converter'),
          ),
          drawer: const DrawerTemplate(),
          body: DollarsPage(),
          ),
        ),
    );
  }
}

class DollarsPage extends StatefulWidget {
  const DollarsPage({ Key? key }) : super(key: key);

  @override
  State<DollarsPage> createState() => _DollarsPageState();
}

class _DollarsPageState extends State<DollarsPage> {
  final TextEditingController dollarsController = TextEditingController();
  final TextEditingController eurosController = TextEditingController();

  List<TextFieldTemplate> textFields = []; 
  
  @override
  void initState() {
   textFields = [
      TextFieldTemplate(
        title: 'Dollars',
        controller: dollarsController,
        factor: 0.93,
      ),
      TextFieldTemplate(
        title: 'Euros',
        controller: eurosController,
        factor: 1.07,
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
              'dollars to euros'.toUpperCase(), 
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