import 'dart:math';
import 'package:test/test.dart';
import 'path/to/my_other_file.dart';

class MyClass {

  final regex = new RegExp(r'''[a-zA-Z0-9]*]''');

  final String _name = 'Rin';

  final Map<String, String> map = <String, String> {
    'one': 'uno',
    'two': 'dos',
    'three': 'tres',
  };

  final List<String> list = <String> [
    'one',
    'two',
    'three',
    'four',
    'five',
  ];

  String greet() {
    return 'Yoi Yoi';
  }

}
