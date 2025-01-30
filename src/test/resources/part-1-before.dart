part of 'greetings.dart';
/// One
abstract class Animal{}
/// Two
class Cat extends Animal{}
/// Three
class Bird implements Animal{}
/// Four
void hello(){
  stdout.writeln('Hello!');
}