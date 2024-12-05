import 'package:flutter/material.dart';

class TextFieldTemplate extends StatelessWidget {
  final String title;
  final TextEditingController controller; 
  final double factor;

  const TextFieldTemplate({
    Key? key,
    required this.title,
    required this.controller,
    required this.factor,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Center(
      child: Container(
        width: 500,
        margin: const EdgeInsets.all(20),
        child: TextField(
          controller: controller,
          textAlignVertical: TextAlignVertical.center,
          obscureText: false,
          style: const TextStyle(
              fontSize: 30,
              fontWeight: FontWeight.bold,
          ),
          decoration: InputDecoration(
            border: const OutlineInputBorder(),
            labelText: title,
            isDense: true,
            contentPadding: const EdgeInsets.symmetric(vertical: 40),
          ),
        ),
      ),
      );
  }
}