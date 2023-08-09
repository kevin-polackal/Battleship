package cs3500.pa04.model;

/**
 * To represent the six possible method-names given during a run of the server
 */
public enum MethodName {
  JOIN("join"), SETUP("setup"), TAKE_SHOTS("take-shots"), REPORT_DAMAGE("report-damage"),
  SUCCESSFUL_HITS("successful-hits"), END_GAME("end-game");
  public final String methodName;

  MethodName(String name) {
    this.methodName = name;
  }
}
