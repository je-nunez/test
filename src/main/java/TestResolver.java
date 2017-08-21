
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import java.io.OutputStreamWriter;
import java.io.IOException;

import gulava.Goal;
import gulava.Goals;
import gulava.LogicValue;
import gulava.Subst;
import gulava.Var;
import gulava.Cons;
import gulava.View;
import gulava.Replacer;
import gulava.Dumper;
import gulava.Stream;
import gulava.Streams;
import static gulava.Goals.conj;
import static gulava.Goals.same;
import gulava.annotation.MakePredicates;

class Person {
  // a normal Java class
  String name;
  // ... other attributes of Person, like birthdate, etc.

  Person(String name) {
    this.name = name;
  }
}

class Parent {
  // this class represents the web of object relationship in the Java app.
  // See below for the ParentAdapter class, which is the adapter to the
  // Gulava Java-Prolog library. (Alternatively, this Java class Parent can
  // implement the Gulava "LogicValue" interface, but it would then be less
  // of a pure Java-only class and would seem more intromissive and
  // maintenable in the long-term. With the adapter class "ParentAdapter"
  // below, then this class "Parent" would remain being a normal Java class.
  // All depends on how tight the Java class -e.g., Parent- should be with
  // respect of the Gulava Java-Prolog library.)

  Person parent;
  Person child;

  Parent(Person parent, Person child) {
    this.parent = parent;
    this.child = child;
  }

  // ... other methods
} // class Parent

@MakePredicates
class ParentAdapter implements LogicValue {
  private final Parent wrappedParenthood;

  ParentAdapter(Parent realParenthood) {
    wrappedParenthood = realParenthood;
  }

  @Override
  public Map<String, ?> asMap() {
    Map<String, Object> map = new HashMap<>();
    map.put("parent", wrappedParenthood.parent.name);
    map.put("child", wrappedParenthood.child.name);

    return map;
  }

  @Override
  public String toString() {
    return String.format("Parent{%s, %s}",
                         wrappedParenthood.parent.name,
                         wrappedParenthood.child.name);
  }

  @Override
  public int hashCode() {
    return Arrays.hashCode(new Object[]{
                             wrappedParenthood.parent.name,
                             wrappedParenthood.child.name
                           });
  }

  @Override
  public Subst unify(Subst subst, LogicValue other) {
    // TODO: Prolog-style unification
    return null;
  }

  @Override
  public LogicValue replace(Replacer replacer) {
    // TODO
    return new ParentAdapter((Parent) replacer.replace(wrappedParenthood));
  }

  @Override
  public boolean equals(Object o) {
    // TODO
    return false;
  }

}  // class ParentAdapter

public class TestResolver {

  static void print(Stream s, int maxSteps, Var... requestedVars) throws IOException {
    Dumper dumper = new Dumper(0, new OutputStreamWriter(System.out));
    int totalSteps = 0;
    while (totalSteps < maxSteps) {
      if (s == Streams.EMPTY) {
        System.out.println("()");
        break;
      }

      System.out.println("\n----------------- iteration: " + (totalSteps + 1));
      dumper.dump(s);
      dumper.flush();

      Subst subst = s.subst();

      if (subst != null) {
        System.out.println(new View.Builder()
            .setSubst(subst)
            .addRequestedVar(requestedVars)
            .build());
      }
      s = s.rest();
      totalSteps++;
    }
    System.out.println("----------------- Final total steps: " + totalSteps);
  }

  public static void main(String[] args) throws Exception {
    Var a = new Var();
    Var b = new Var();
    Var x = new Var();
    Var y = new Var();

    System.out.println("\nexample 1: unification");
    Goal g = Goals.conj(
        Goals.same(x, y),
        Goals.same(x, Cons.list(Arrays.asList(2, 3, 4))),
        Goals.same(a, Cons.of(42, x)),
        Goals.same(b, Cons.of(43, y)));
    print(g.run(Subst.EMPTY), 10, a, b);

  }

} // class TestResolver
