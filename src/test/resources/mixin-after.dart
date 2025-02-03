abstract class GlobalLog {

  void debug(final String message);

}

mixin Log<T> on Parent implements GlobalLog {

  @override
  void debug(final String message) {
    print('[DEBUG]: $message');
  }

}

class Parent {

}

class User extends Parent with Log {

  void hello() {
    debug('Hello');
  }

}
