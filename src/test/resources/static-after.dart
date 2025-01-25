class Number {

  static int of(final bool value) {
    return value ? 1 : 0;
  }

}

void main() {
  print(
    Number.of(true)
  );
}
