class User {
  var items=Items();
}
class Items {
  var map={};
}
void main(){
  var user=User();
  user.items.map['one']='uno';
}