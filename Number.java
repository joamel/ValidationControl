package sample;

/**
 * Basklass som övriga subklasser ärver av
 */
public abstract class Number {

    /**
     * Deklarerar instansvariabler
     */
    protected String number, tempNumb, error;
    protected boolean isValidFormat, isValidDate, isNumerical, isValidControlNumber;

    /**
     * I Konstruktorn tilldelas objektet instansvariabler
     * @param nr är det angivna numret från användaren
     */
    public Number(String nr) {
        this.number = nr;
        this.isValidFormat = false;
        this.isValidDate = false;
        this.isNumerical = false;
        this.isValidControlNumber = false;
        this.error = "";
    }


    /**
     * Abstrakt metod som startar upp valideringen
     * @return boolean
     */
    protected abstract boolean validate();


    /**
     * Metod som kontrollerar att kontrollsiffran är korrekt
     */
    protected void checkControlNumber() {
        if (tempNumb.length() == 12)
            tempNumb = tempNumb.substring(2);
        int sum = 0;
        //For-loop som går igenom hela numret utom sista siffran
        for (int i = 0; i < tempNumb.length() - 1; i++) {
            int c = 0;
            //Varannan siffra multipliceras med 2
            if (i % 2 == 0) {
                c = Integer.parseInt(String.valueOf(tempNumb.charAt(i))) * 2;
            }
            else {
                c = Integer.parseInt(String.valueOf(tempNumb.charAt(i)));
            }
            if (c > 9) {
                c = c - 9;
            }
            sum = sum + c;
        }
        int controlNumber = (10 - (sum % 10) % 10);

    if (Integer.parseInt(String.valueOf(tempNumb.charAt(tempNumb.length() - 1))) == controlNumber || controlNumber == 10) {
            isValidControlNumber = true;
        }
        else {
            isValidControlNumber = false;
            error = "Fel kontrollsiffra";
        }
    }


    /**
     * Metod som kontrollerar att numret är numeriskt
     * @return boolean
     */
    protected boolean checkNumerical() {
        if (tempNumb.matches("[0-9]+")) {
            isNumerical = true;
        }
        else {
            error = "Inte bara siffror";
            return false;
        }
        return true;
    }


    /**
     * Abstrakt metod som kontrollerar giltigheten av datum i numret
     * @return boolean
     */
    protected abstract boolean validDate();


    /**
     * Getter till felet
     * @return String error
     */
    public String getError() {
        return error;
    }


    /**
     * Getter till angivna numret
     * @return String number
     */
    public String getNumber() {
        return number;
    }
}
