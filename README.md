# Runtime Verification: A Hands-On Approach in Java — exercise code

Welcome! This is the code accompanying the book *Runtime Verification: A
Hands-On Approach in Java*.

As explained in Chapter 3 of the text, each folder here corresponds to a book
exercise. Simply import the code into your favourite IDE, read the book, and
start coding away :)

The `*-solutions` folders offer guidance where you get stuck, and come in
handy if you have skipped any chapters which are a prerequisite for later
ones.

This is teaching code and deliberately has **no automated test suite** — the
point of each chapter is for you to run it yourself (via `fits.Main`, or the
chapter-specific tool named in its own `readme.txt`) and see the runtime
verification in action.

## Building with Maven

Every chapter folder is its own **standalone** Maven project — there is no
shared parent POM and no multi-module build tying chapters together, so one
chapter's state can never affect another's:

```sh
cd chapter06-solutions
mvn compile
```

(There's deliberately no `pom.xml` at the repository root — `mvn compile`
from here just errors, telling you to `cd` into a chapter first, rather
than silently trying to build all 47 at once.)

**A chapter you haven't finished yet is *expected* to fail to build** —
e.g. `chapter06`, where `Properties.aj` is intentionally an incomplete
stub, or `chapter08`/`chapter09`, where the automaton/LTL translation still
has unimplemented methods, or `chapter13-persistence`, which doesn't ship
a `Verification` class yet. That's the exercise itself: the compiler
errors point you at exactly what's still missing, and the corresponding
`-solutions` folder builds cleanly for comparison. That failure is entirely
local to that one chapter's own `mvn compile` — it has no way to affect
any other chapter, since each is built completely independently.

Chapters that weave an AspectJ aspect (`monitoredfits/rv/Properties.aj` or
similar) use the [aspectj-maven-plugin](https://github.com/eclipse-aspectj/aspectj-maven-plugin)
to compile and weave in one step; plain chapters just use the standard Java
compiler. After building, run the class named in that chapter's `readme.txt`
directly, e.g.:

```sh
java -cp chapter06-solutions/target/classes:$(find ~/.m2 -name 'aspectjrt-*.jar' | head -1) fits.Main
```

An IDE with AspectJ support (e.g. Eclipse + AJDT) still works exactly as
before if you prefer that workflow — Maven is an addition, not a replacement.

## Working directory

Unless a chapter's own `readme.txt` says otherwise, run everything — the
compiler tool, `fits.Main`, any other `Main` class — with **that chapter's
own folder as the working directory** (i.e. import/open each chapter as one
project/module, not its subfolders individually). All the file paths inside
the code — `script/properties.egcl`, `monitoredfits/rv/Properties.aj`, and
so on — are written relative to that folder, and running from anywhere else
will fail with a plain `FileNotFoundException` and no other clue why. Maven
already gets this right automatically (each module's working directory is
its own chapter folder); this mainly matters if you're launching classes
by hand from an IDE.

## Chapter index

| Folder | Topic |
| --- | --- |
| `chapter03` | The FiTS banking system, unmonitored |
| `chapter04` | Manual (inline) runtime checks |
| `chapter05`, `chapter05-inlined` | Monitoring via AspectJ |
| `chapter06`, `chapter06-parameterised` | The EGCL property language and its compiler (`MyRVTool`) |
| `chapter07`, `chapter07-parameterised` | Automata-based properties, compiled down to EGCL |
| `chapter08`, `chapter08-parameterised` | Regular-expression properties |
| `chapter09`, `chapter09-parameterised` | LTL properties |
| `chapter10` | Timers |
| `chapter11-checkpointing` | Checkpointing |
| `chapter11-compensations` | Compensations |
| `chapter11-enforcement` | Enforcement |
| `chapter11-mop` | Parametric (MOP-style) properties |
| `chapter11-reparations` | Reparations |
| `chapter12`, `chapter12-systemstate` | Offline monitoring (logging and replaying events) |
| `chapter13-testing` | Testing the monitor itself |
| `chapter13-distributed` | Distributed monitoring |
| `chapter13-overheads` | Measuring monitoring overhead |
| `chapter13-persistence` | Persisting monitor state |

Each `-solutions` (or `-solution`) folder is the completed version of the
sibling exercise folder.

## Acknowledgements

Prof. Owolabi Legunsen from Cornell University has kindly "mavenised" an
earlier version of this code independently; if you'd like to see that
version too, it's available at
[owolabileg/fits-in-maven](https://github.com/owolabileg/fits-in-maven).

## Contact

Christian Colombo and Gordon J. Pace

christian.colombo@um.edu.mt
gordon.pace@um.edu.mt

If you have any questions or comments please don't hesitate to get in touch!
