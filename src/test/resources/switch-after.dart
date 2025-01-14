class User {

  int toNumber(final String number) {
    int result;
    switch (number) {
      case 'one':
        result = 1;
      case 'two':
        result = 2;
      case 'three':
        result = 3;
      default:
        result = -1;
    }
    return result;
  }

}
