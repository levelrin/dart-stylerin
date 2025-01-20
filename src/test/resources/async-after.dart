import 'dart:async';

Future<String> fetchData() async {
  await Future.delayed(
    Duration(
      seconds: 1
    )
  );
  return 'apple';
}

void main() async {
  final String fruit = await fetchData();
  print(fruit);
}
