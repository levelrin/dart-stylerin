// OP CP
void Function() func1 = () => print('');

// OP normalParameterTypes C optionalParameterTypes CP
void Function(int, String, [double]) func2 = (int a, String b, [double? c]) => print('');

// OP normalParameterTypes C? CP
void Function(int, String) func3 = (int a, String b) => print('');

// OP optionalParameterTypes CP
void Function([int, String]) func4 = ([int? a, String? b]) => print('');
