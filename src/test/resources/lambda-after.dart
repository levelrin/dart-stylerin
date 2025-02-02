class Count {

  int _num = 0;

  final List<void Function(int num)> _events = <void Function(int num)>[];

  void increment() {
    this._num++;
    for (final void Function(int num) event in this._events) {
      event(this._num);
    }
  }

  void addEvent(final void Function(int num) event) {
    this._events.add(event);
  }

}

void run(void Function() func) {
  func();
}

void main() {
  final Count count = Count();
  count.addEvent(
    (n) => print(n)
  );
  count.increment();
  bool flag = true;
  run(() {
    flag = false;
  });
}
