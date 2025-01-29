class User {

  const User(this._name);

  const User.withDefaultName() : this._name = 'Anonymous';

  User.withNumber(final int number) : this._name = number.toString();

  final String _name;

  void introduce() {
    print('Hi, my name is ' + _name + '!');
  }

}

class Person {

  final String _name;

  Person.primary(this._name);

}

class Child extends Person {

  Child() : super.primary('Rin') {
    print("Child constructor executed.");
  }

}

class Animal {

  final String _name;

  Animal(this._name);

}

class Cat extends Animal {

  Cat() : super('Chipi') {
    print("Cat constructor executed.");
  }

}

void main() {
  const User user = const User('Rin');
  user.introduce();
  const User user2 = const User.withDefaultName();
  user2.introduce();
}
