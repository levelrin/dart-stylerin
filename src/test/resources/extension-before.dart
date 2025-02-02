extension StringExtension on String{
  String withPrefix(final String prefix) {
    return '$prefix$this';
  }
}
void main() {
  print(
      'World!'.withPrefix('Hello, ')
  );
}