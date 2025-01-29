abstract class Animal{
  String get name;
  void move();
}
class Cat implements Animal{
  @override
  void move() {
print("cat moved!");
  }
  @override
  String get name   =>throw UnimplementedError('No name given.');
}
void main(){
  final Animal animal = Cat();
animal.move();
}