class Parent{int money(){return 3;}
String familyName() {
return'yoi';
}}

class Child extends Parent {
  static final tag='TAG';
  @override
  int money(){return super.money()+1;}
  @override
  String familyName(){return 'yo';}
}