class RussianDoll{
  final String _name;
  final RussianDoll? _child;
  const RussianDoll(this._name,[this._child]);
  RussianDoll? child(){return this._child;}
  void unwrap(){
    print(this._name);
    if(this._child!=null){this._child.unwrap();}
  }
}
void main(){
  final RussianDoll doll=RussianDoll('Rin',RussianDoll(
    'Revomin',RussianDoll('Ian')));
  doll.unwrap();
}