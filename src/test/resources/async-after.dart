import 'dart:async';

class Basket {

  Future<String> Function()? onFetch;

  Basket({
    this.onFetch
  });

}

Future<String> fetchData() async {
  await Future.delayed(
    Duration(
      seconds: 1
    )
  );
  return 'apple';
}

Future<void> sendMessage(final String url, {
  final Map<String, String>? options
}) async {
  print('yoi');
}

void main() async {
  final String fruit = await fetchData();
  print(fruit);
  Basket(
    onFetch: () async {
      await Future.delayed(
        Duration(
          seconds: 1
        )
      );
      return 'banana';
    }
  );
  await sendMessage(
    'http://www.google.com',
    options: <String, String>{
      'abc': 'def',
    }
  );
}
