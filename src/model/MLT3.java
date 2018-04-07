package model;

public class MLT3 {

  private static Boolean lastNonZero;
  private static String message;
  private static String stateActual;
  
  public static String encode(String messageBin) {
    lastNonZero = true;
    message = "";
    stateActual = "1";
    encodeMessage(messageBin);
    return message;
  }
  
  public static String decode(String signal) {
    message = "";
    stateActual = "1";
    decodeMessage(signal);
    return message;
  }
  
  private static void decodeMessage(String signal) {
    for(int i = 0 ; i < signal.length() ; i++) {
      if(i%8 == 0 && i != 0)
        message += " ";
      message += signal.substring(i, i+1).equals(stateActual) ? "0" : "1";
      stateActual = signal.substring(i, i+1);
    }
  }
  
  private static void encodeMessage(String messageBin) {
    for(int i = 0 ; i < messageBin.length() ; i++) {
      if(messageBin.charAt(i) != ' ')
        message += (stateActual = (messageBin.charAt(i) == '0' ? stateActual : isOne()));
    }
  }
  
  private static String isOne() {
    if(stateActual.equals("0")|| stateActual.equals("2"))
      return "1";
    else {
      lastNonZero = !lastNonZero;
      if(lastNonZero)
        return "0";
      else
        return "2";
    }
  }
  
  public static void main(String[] args) {
    String message = "10101010 01010100";
    System.out.println(message = encode(message));
    System.out.println(decode(message));
  }
  
}

