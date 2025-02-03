class Animal {

  final Bird child;

  Animal({
    required this.child
  });

}

class Bird<T> {

  const Bird.stuff();

}

void main() {
  Animal(
    child: const Bird<int>.stuff(),
  );
}
