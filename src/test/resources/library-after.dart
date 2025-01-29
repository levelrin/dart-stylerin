library greetings;

import 'dart:io';

part 'hello.dart';
part 'bye.dart';

void greet() {
  stdout.writeln('Start');
  hello();
  bye();
}
