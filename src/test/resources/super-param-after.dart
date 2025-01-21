class Parent {

  final String? lastName;

  const Parent({
    this.lastName
  });

}

class Child extends Parent {

  final String firstName;

  const Child({
    required this.firstName,
    super.lastName,
  });

}
