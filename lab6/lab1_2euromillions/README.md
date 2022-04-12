# Lab 6
## 6.2 Local analysis


### (f)

My project passed the defined quality gate with the following results:
- 1 bug (D reliability)
- 0 vulnerabilities (A security)
- 1 security hotspot and 0.0% reviewed (E Security Review)
- 2h42m of Debt and 38 code smells (A Maintainability)
- 75.3% coverage on 145 lines to cover by 11 unit tests
- 0.0% duplications on 313 lines (0 duplication blocks)

### (g)

| issue  | problem description  | how to solve  |
|---|---|---|
| bug  | Save and re-use this “Random”.  | "Random" objects should be reused. Creating a new Random object each time a random value is needed is inefficient and may produce numbers which are not random depending on the JDK.  |
| security hotspot  | Make sure that using this pseudorandom number generator is safe here. (This is again referring to the Random() line.) | Math.random is not a true random generator but a PRNG (pseudorandom number generator). To be safe, one needs a CSPRNG (cryptographically secure pseudorandom number generator).  |
| code smell (major)  | Refactor the code in order to not assign to this loop counter from within the loop body.  | "for" loop stop conditions should be invariant. A for loop stop condition should test the loop counter against an invariant value (i.e. one that is true at both the beginning and ending of every loop iteration).  |
| code smell (major)  | Replace this use of System.out or System.err by a logger.  | Standard outputs should not be used directly to log anything.  |
| code smell (major)  | Use assertEquals instead. The line of code: ``assertTrue(actualMessage.equals(expectedMessage));``  | JUnit assertTrue/assertFalse should be simplified to the corresponding dedicated assertion.  |