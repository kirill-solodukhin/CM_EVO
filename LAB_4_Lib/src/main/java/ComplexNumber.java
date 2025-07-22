public class ComplexNumber
{
    private final double real;
    private final double imaginary;
    private double accuracy = 0.000001;

    public ComplexNumber(double real, double imaginary)
    {
        this.real = real;
        this.imaginary = imaginary;
    }

    public ComplexNumber(double real, double imaginary, double accuracy)
    {
        this.real = real;
        this.imaginary = imaginary;
        this.accuracy = accuracy;
    }

    public ComplexNumber add(ComplexNumber num)
    {
        double r  = real + num.getReal();
        double i  = imaginary + num.getImaginary();

        return new ComplexNumber(r, i);
    }

    public ComplexNumber add(double num)
    {
        double r = real + num;

        return new ComplexNumber(r, imaginary);
    }

    public ComplexNumber subtract(ComplexNumber num)
    {
        double r = real - num.getReal();
        double i = imaginary - num.getImaginary();

        return new ComplexNumber(r, i);
    }

    public ComplexNumber subtract(double num)
    {
        double r = real - num;

        return new ComplexNumber(r, imaginary);
    }

    public String toString()
    {
        if(imaginary > -accuracy && imaginary < accuracy) // im == 0
        {
            return real + "";
        }

        if(imaginary > accuracy) // im > 0
        {
            return real + " + i" + imaginary;
        }

        return real + " - i" + -imaginary;
    }

    public double getReal()
    {
        return real;
    }

    public double getImaginary()
    {
        return imaginary;
    }
}
