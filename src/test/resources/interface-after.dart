abstract class Animal {

  void move();

}

class Cat implements Animal {

  @override
  void move() {
    print("cat moved!");
  }

}

void main() {
  final Animal animal = Cat();
  animal.move();
}
