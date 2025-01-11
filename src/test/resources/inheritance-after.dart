class Parent {

  int money() {
    return 3;
  }

}

class Child extends Parent {

  @override
  int money() {
    return 1;
  }

}
