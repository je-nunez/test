
import java.util.Arrays;

import java.io.OutputStreamWriter;
import java.io.IOException;

import gulava.Goal;
import gulava.Goals;
import gulava.Subst;
import gulava.Var;
import gulava.Var;
import gulava.Cons;
import gulava.View;
import gulava.Dumper;
import gulava.Stream;
import gulava.Streams;
import static gulava.Goals.conj;
import static gulava.Goals.same;
import gulava.annotation.MakePredicates;

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

  @MakePredicates
  public static abstract class Parent {

      public abstract Goal parent(Object parent, Object child);

      final Goal parent_fact_1(Object parent, Object child) {
          // TODO
          return Goals.UNIT;
      }

      final Goal parent_fact_2(Object parent, Object child) {
          // TODO
          return Goals.UNIT;
      }

      final Goal parent_fact_3(Object parent, Object child) {
          // TODO
          return Goals.UNIT;
      }
  }

}

