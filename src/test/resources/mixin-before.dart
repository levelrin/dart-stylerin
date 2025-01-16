mixin Log{void debug(final String message){print('[DEBUG]: $message');}}
class User with Log{void hello(){debug('Hello');}}