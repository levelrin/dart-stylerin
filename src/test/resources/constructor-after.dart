class User {

  const User(this._name);

  final String _name;

  void introduce() {
    print('Hi, my name is ' + _name + '!');
  }

}

void main() {
  const User user = const User('Rin');
  user.introduce();
}
