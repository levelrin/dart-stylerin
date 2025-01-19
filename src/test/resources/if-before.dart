class MyClass{
  String toFruit(final int index) {
      String fruit;
    if(index == 0){
      fruit = 'apple';}else if(index==1){fruit='banana';} else if (index==2||index==3){
      fruit = 'kiwi';} else if (index>3&&index<7){fruit = 'grape';}else{
      fruit = 'orange';
    }return fruit;
  }
}