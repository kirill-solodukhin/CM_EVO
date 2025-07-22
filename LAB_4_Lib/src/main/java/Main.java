import Lib.ComplexNumber;

public class Main
{
    public static void main(String[] args)
    {
        ComplexNumber num_1 = new ComplexNumber(10, -11);
        ComplexNumber num_2 = new ComplexNumber(10, 11);
        ComplexNumber i = new ComplexNumber(0, 1);

        show(num_1, num_2, i);
    }

    public static void show(ComplexNumber num_1, ComplexNumber num_2, ComplexNumber num_3)
    {
        System.out.println("Your first number: " + num_1);
        System.out.println("Your second number: " + num_2);
        System.out.println("Your third number: " + num_3 + '\n');

        System.out.println("Third number is real: " + num_3.isReal() + '\n');


        System.out.println("___________________ ADD __________________");
        ComplexNumber number = num_1.add(num_2);
        System.out.println("number = num_1 + num_2 = ( " + num_1  + " ) + ( " + num_2 + " ) = " + number);
        System.out.println("number is real?: " + number.isReal());

        number = number.add(num_3);
        System.out.println("Number += num_3 = " + number);
        System.out.println("Number is imaginary?: " + number.isImaginary());
        System.out.println("Number is real?: " + number.isImaginary());

        number = number.add(2);
        System.out.println("Number += 2 = " + number+ '\n');

        System.out.println("___________________ Subtract __________________");
        number = number.subtract(3);
        System.out.println("Number -= 3 = " + number);

        number = number.subtract(num_2);
        System.out.println("Number = ( 19.0 + i1.0) - ( " + num_2 + " ) = " + number + '\n');

        System.out.println("___________________ Multiplex __________________");
        number = num_1.multiplex(num_2);
        System.out.println("number = num_1 * num_2 = ( " + num_1  + " ) * ( " + num_2 + " ) = " + number);
        number = num_1.multiplex(5);
        System.out.println("number = num_1 * 5 = ( " + num_1  + " ) * ( " + 5 + " ) = " + number + '\n');

        System.out.println("___________________ Divide __________________");
        number = num_1.divide(num_2);
        System.out.println("number = num_1 / num_2 = ( " + num_1  + " ) / ( " + num_2 + " ) = " + number);
        number = num_1.divide(2);
        System.out.println("number = num_1 / 2 = ( " + num_1  + " ) / ( " + 2 + " ) = " + number);

    }
}
