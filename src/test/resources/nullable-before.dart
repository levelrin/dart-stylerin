void main(){
  String?nullStr=null;
  String?solidStr=nullStr?.replaceAll('','')??'solid';
  String confirmedStr=solidStr!;
  print(confirmedStr);
}