void Function([String? a, double? b, int? c,]) optionalPositionalParameterTypes = ([String? a, double? b, int? c,]) => print('');

void Function({
  required int num,
  required String name,
  required double point,
}) namedParameterTypes = ({
  int num = 1,
  String name = 'Rin',
  double point = 3.14,
}) => print('');
