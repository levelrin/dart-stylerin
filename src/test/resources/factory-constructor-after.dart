class User {

  factory User.withDefault() {
    return User.withRaw('Rin');
  }

  const User.withRaw(this._name);

  final String _name;

  void introduce() {
    print('Hi, my name is ' + _name + '!');
  }

}
