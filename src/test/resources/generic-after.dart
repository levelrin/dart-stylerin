class Key<T> {

  Key();

}

class User {

  static final Key<int> key = Key();

}

T genericMethod<T>(final T value) {
  return value;
}

void main() {
  print(
    genericMethod<String>("Yoi Yoi")
  );
}
