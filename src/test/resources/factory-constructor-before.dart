class User {
  /// Secondary constructor.
  /// It's for production.
  factory User.withDefault(){return User.withRaw('Rin');}
  /// Primary constructor.
  /// It's for testing.
  const User.withRaw(this._name);
  final String _name;
  void introduce(){print('Hi, my name is '+_name+'!');}
}