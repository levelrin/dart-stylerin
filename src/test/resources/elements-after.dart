var list1 = [
  1,
  2,
  3,
  4,
];

var list2 = [
  0,
  {},
  ...list1,
  if (true) {
    5
  },
  for (int index = 6; index < 9; index++) {
    index,
  }
];

void main() {
  print(list2);
}
