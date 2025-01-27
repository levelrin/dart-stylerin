class User{
  const User(this._name);
  const User.withDefaultName():this._name='Anonymous';
  final String _name;
  void introduce(){print('Hi, my name is '+_name+'!');}
}
void main(){
  const User user=const User('Rin');
  user.introduce();
  const User user2 = const User.withDefaultName();
  user2.introduce();
}