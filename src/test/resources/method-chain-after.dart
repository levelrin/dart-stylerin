void main() {
  final List<String> fruits = <String>[];
  fruits..add('apple');
  fruits..add('banana')..add('orange');
  fruits.sublist(0);
  fruits.sublist(0).sublist(1);
  fruits.sublist(0)..add('kiwi');
}
