/*
 * Copyright (c) 2020 Rin (https://www.levelrin.com)
 *
 * This file has been created under the terms of the MIT License.
 * See the details at https://github.com/levelrin/DartStyleRin/blob/master/LICENSE
 */

import 'package:dart_style_rin/source/silent_source.dart';
import 'package:test/test.dart';

void main() {
  group('SilentSource', () {
    test('.lines() should not throw an exception.', () {
      expect(
        () => SilentSource().lines(),
        returnsNormally
      );
    });
    test('.toString() should not throw an exception.', () {
      expect(
          () => SilentSource().toString(),
        returnsNormally
      );
    });
  });
}
