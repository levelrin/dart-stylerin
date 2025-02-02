import 'dart:math';

void riskyOperation() {
  var random = Random();
  var choice = random.nextInt(4);
  if (choice == 0) {
    throw FormatException('Invalid format encountered');
  } else if (choice == 1) {
    throw RangeError.value(100, 'Input value', 'Value is out of acceptable range');
  } else if (choice == 2) {
    throw Exception('General exception occurred');
  } else {
    print('Risky operation completed successfully');
  }
}

void main() {
  try {
    print('Attempting to execute risky operation');
    riskyOperation();
  } on FormatException catch (e) {
    print('Caught FormatException');
    print('Error message: ${e.message}');
  } on RangeError catch (e) {
    rethrow;
  } on Exception catch (e) {
    print('Caught Exception');
    print('Error: ${e.toString()}');
  } catch (e, stackTrace) {
    print('Caught unknown error');
    print('Error: ${e.toString()}');
    print('StackTrace: ${stackTrace.toString()}');
  } finally {
    print('Finally block executed');
  }
}
