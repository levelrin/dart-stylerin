class Food {

  @override
  String toString() {
    return 'food';
  }

}

class Fish extends Food {

  @override
  String toString() {
    return 'fish';
  }

}

class Animal {

  void eat(covariant Food food) {
    print('Eat $food');
  }

}

class Cat extends Animal {

  @override
  void eat(Fish food) {
    print('Eat $food');
  }

}

void main() {
  final Animal cat = Cat();
  final Food fish = Fish();
  cat.eat(fish);
}
