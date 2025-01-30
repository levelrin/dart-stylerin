class User{
  final int id;
  final String name;
  final String position;
  final String nickName;
  const User({required this.id,required this.name,required this.position,this.nickName='nick',});
  void introduce(){print('id: ${this.id}, name: ${this.name}, position: ${this.position}');}
}
void main(){
  final User user = User(id: 0,name: 'Rin',position: 'Developer',);
  user.introduce();
}