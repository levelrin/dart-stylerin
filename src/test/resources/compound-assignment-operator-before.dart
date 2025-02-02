void main() {
String a = '1';
a *= 2;
print(a);
double b = 8;
b /= 2;
print(b);
int c = 10;
c ~/= 3;
c %= 3;
c += 4;
c -= 1;
c <<= 1;
c >>>= 1;
c >>= 1;
c &= 1;
c ^= 1;
c |= 1;
print(c);
String? d = null;
d ??= 'hi';
print(d);
}