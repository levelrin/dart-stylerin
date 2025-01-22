class MyClass {

  int _num = 0;

  String greet() {
    return 'Yoi Yoi';
  }

  int sum(final int a, final int b) {
    return a + b;
  }

  static String tag() => 'TAG';

  int get num => this._num;

  set num(final int value) => this._num = value;

}

void main() {
  MyClass obj = MyClass();
  obj.num = 3;
  print(obj.num);
  final int value1 = (obj.num + 3) * 2;
  print(value1);
}
