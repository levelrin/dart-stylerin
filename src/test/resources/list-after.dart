class User {

  String? name;

  User? child;

  User({
    this.name,
    this.child
  });

}

class MyClass {

  final List<dynamic> list = <dynamic>[
    'one',
    'two',
    'three',
    'four',
    'five',
    User(
      name: 'Rin',
      child: User(
        name: 'Revomin'
      )
    ),
  ];

}
