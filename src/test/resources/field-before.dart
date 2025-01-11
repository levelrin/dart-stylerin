class MyClass{
  final regex=new RegExp(r'''[a-zA-Z0-9]*]''');
  final String _name='Rin';
  final String Function(String name)withName=(final String name){return 'Hello, $name!';};
}