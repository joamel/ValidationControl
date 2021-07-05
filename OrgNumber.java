package sample;

/**
 * Subklass som ärver från Numbers. Skapar upp ett objekt organisationsnummer med validerande metoder.
 */
public class OrgNumber extends Number {
    //Deklarerar instansvariabel
    private boolean isValidTwenty;

    /**
     * Objeketet tilldelas instansvariabler och åberopar basklassens konstruktor
     */
    public OrgNumber(String nr) {
        super(nr);
        this.isValidTwenty = false;
    }


    /**
     * Metod som startar upp valideringen
     * @return boolean av alla kriterier
     */
    public boolean validate() {
        tempNumb = number;

        //Validerar de olika kriterierna
        if (checkFormat())
            if (checkNumerical())
                if (validDate())
                    if (checkTwenty())
                        checkControlNumber();

        //Returnerar slutligen alla
        return this.isValidFormat && this.isValidTwenty && this.isValidDate && isNumerical && this.isValidControlNumber;
    }


    /**
     * Metod som kontrollerar att mittersta sifferparet är minst 20.
     * @return boolean
     */
    private boolean checkTwenty() {
        if (Integer.parseInt(Character.toString(tempNumb.charAt(tempNumb.length() - 8)) + tempNumb.charAt(tempNumb.length() - 7)) > 19) {
            isValidTwenty = true;
            return true;
        }
        error = "Mittersta sifferparet måste vara minst 20";
        return false;
    }


    /**
     *Metod som kontrollerar att de org.nummer som är 12 tecken långa börjar på mellan 16 - 20
     * @return boolean
     */
    protected boolean validDate() {
        if (tempNumb.length() == 12) {
            if (Integer.parseInt(Character.toString(tempNumb.charAt(0)) + tempNumb.charAt(1)) > 15 &&
                    Integer.parseInt(Character.toString(tempNumb.charAt(0)) + tempNumb.charAt(1)) < 21)
                isValidDate = true;
            else {
                error = "Ej giltigt år";
                return false;
            }
        }
        isValidDate = true;
        return true;
    }


    /**
     * Metod som kontrollerar giltigheten på numrets format.
     * @return boolean
     */
    protected boolean checkFormat() {
        if (tempNumb.length() == 0) {
            error = "Kan ej vara blank";
            return false;
        }

        else if (!(tempNumb.length() == 11) && !(number.length() == 13)) {
            error = "Inkorrekt format";
            return false;
        }

        else if (Character.toString(number.charAt(number.length() - 5)).equals("-")) {
            tempNumb = tempNumb.replace("-", "");
        }

        else {
            error = "Inkorrekt format";
            return false;
        }
        isValidFormat = true;
        return true;
    }
}
