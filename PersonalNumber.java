package sample;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Subklass som ärver från Numbers. Skapar upp ett objekt personnummer med validerande metoder.
 */
public class PersonalNumber extends Number {
    //Deklarerar instansvariabel
    private boolean isValidDate;
    private Date today = new Date();
    DateFormat format;

    /**
     * Konstruktorn åberopar basklassens konstruktor
     */
    public PersonalNumber(String nr) {
        super(nr);
    }


    /**
     * Metod som startar upp valideringen
     * @return boolean av alla kriterier
     */
    public boolean validate() {
        //Skapar en kopia av number för att inte åverka på användarens inmatade nummer
        tempNumb = number;

        //Validerar de olika kriterierna
        if (checkFormat())
            if (checkNumerical())
                if (validDate())
                    checkControlNumber();

        return this.isValidDate && isNumerical && this.isValidControlNumber;
    }

    protected boolean validDate() {
        try {
            if (checkDate(tempNumb)) {
                isValidDate = true;
            }
        }
        catch (ParseException e){
            error = "Datum ogiltigt";
        }
        return true;
    }

    private boolean checkDate(String date) throws ParseException {
        DateFormat format;
        Date today = new Date();
        //Tar bort 4 sista siffrorna i numret
        date = date.substring(0,date.length()-4);

        if (date.length() == 8) {
            format = new SimpleDateFormat("yyyyMMdd");
        }
        else {
            format = new SimpleDateFormat("yyMMdd");
        }
        //setLenient false tillåter bara exakta datum
        format.setLenient(false);
        Date birthYear = format.parse(date);

        //OK om dagens datum är senare än födelsedatum
        if (today.after(birthYear)){
            return true;
        }
        isValidDate = false;
        error = "Ej giltigt datum";

        return false;
    }

    /**
     * Metod som kontrollerar giltigheten på numrets format.
     * @return boolean
     */
    private boolean checkFormat() {
        if (tempNumb.length() == 0) {
            error = "Kan ej vara blank";
            return false;
        }
        //Om numret är 11 eller 13 tecken långt och har ett "-" mellan födelsedatum och 4 sista siffrorna
        else if ((tempNumb.length() == 11 && Character.toString(tempNumb.charAt(6)).equals("-")) ||
                tempNumb.length() == 13 && Character.toString(tempNumb.charAt(8)).equals("-")) {
            //Enbart 1800-,1900- och 2000-tal är godkända födelseår
            if (tempNumb.length() == 13 && !(Integer.parseInt(String.valueOf(tempNumb.charAt(0)) + tempNumb.charAt(1)) > 17 &&
                    Integer.parseInt(String.valueOf(tempNumb.charAt(0)) + tempNumb.charAt(1)) < 21)) {
                error = "Århundrade ej tillåtet";
                return false;
            }
            //Tar bort 5 tecknet räknat bakifrån
            tempNumb = tempNumb.substring(0, tempNumb.length() - 5) + tempNumb.substring(tempNumb.length() - 4);

            if (!checkNumerical()) {
                error = "Inkorrekt format";
                return false;
            }
        }

        else if (tempNumb.length() == 11 && Character.toString(number.charAt(6)).equals("+")) {
            //Tar fram gällande århundraden 20, 19, 18
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(today);
            int century = (calendar.get(Calendar.YEAR) + 99 / 100);
            String cent = String.valueOf(century).substring(0,2);
            String cent1 = String.valueOf(Integer.parseInt(cent) - 1);
            String cent2 = String.valueOf(Integer.parseInt(cent) - 2);
            //Lägger till gällande århundrade till ett temporärt datum för att jämföra med dagens datum.
            String tempString = cent + tempNumb.substring(0,tempNumb.length() - 5) + tempNumb.substring(tempNumb.length() - 4);
            format = new SimpleDateFormat("yyyyMMdd");

            try {
                Date tempDate = format.parse(tempString);
                //Om dagens datum är äldre än tempDate måste person vara född två århundraden sedan
                if (today.before(tempDate))
                    tempNumb = cent2 + tempNumb.substring(0,tempNumb.length()-5) + tempNumb.substring(tempNumb.length()-4);
                    //annars är personen född under förra seklet
                else {
                    tempNumb = cent1 + tempNumb.substring(0,tempNumb.length()-5) + tempNumb.substring(tempNumb.length()-4);
                }

            } catch (ParseException e) {
                error = "Ej giltigt datum";
                return false;
            }
        }

        else if (tempNumb.length() == 10 || tempNumb.length() == 12) {
            if (!checkNumerical()) {
                error = "Inkorrekt format";
                return false;
            }
        }

        else {
            error = "Inkorrekt format";
            return false;
        }
        isValidFormat = true;
        return true;
    }
}
