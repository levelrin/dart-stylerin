void main() {
  Map<String, int>? map = {
    'one': 1
  };
  int num = map?['one'] ?? 0;
}
