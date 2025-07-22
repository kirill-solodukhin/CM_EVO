package Lib;

public class ComplexNumber
{
    private final double real;
    private final double imaginary;
    private double accuracy = 0.00000001;

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
        double _real  = real + num.getReal();
        double _imaginary  = imaginary + num.getImaginary();

        return new ComplexNumber(_real, _imaginary);
    }

    public ComplexNumber add(double num)
    {
        double _real = real + num;
        return new ComplexNumber(_real, imaginary);
    }

    public ComplexNumber subtract(ComplexNumber num)
    {
        double _real = real - num.getReal();
        double _imaginary = imaginary - num.getImaginary();

        return new ComplexNumber(_real, _imaginary);
    }

    public ComplexNumber subtract(double num)
    {
        double _real = real - num;
        return new ComplexNumber(_real, imaginary);
    }

    public ComplexNumber multiplex(ComplexNumber num)
    {
        double _real = (real * num.getReal() - imaginary * num.getImaginary());
        double _imaginary = (real * num.getImaginary() + num.getReal() * imaginary);

        return new ComplexNumber(_real, _imaginary, accuracy);
    }

    public ComplexNumber multiplex(double num)
    {
        double _real = real * num;
        double _imaginary = imaginary * num;

        return new ComplexNumber(_real, _imaginary, accuracy);
    }

    public ComplexNumber divide(ComplexNumber num)
    {
        if(num.equals(0))
        {
            throw new DivideByZeroException("Divide by zero");
        }

        double _real = ((real * num.getReal()) + (imaginary * num.getImaginary())) /
                (Math.pow(num.getReal(), 2) + Math.pow(num.getImaginary(), 2));

        double _imaginary = ((imaginary * num.getImaginary()) - (real * num.getImaginary())) /
                (Math.pow(num.getReal(), 2) + Math.pow(num.getImaginary(), 2));

        return new ComplexNumber(_real, _imaginary, accuracy);
    }

    public ComplexNumber divide(double num)
    {
        if(num == 0)
        {
            throw new DivideByZeroException("Divide by zero");
        }

        return new ComplexNumber(real / num, imaginary / num, accuracy);
    }

    public ComplexNumber complexConjugate()
    {
        return new ComplexNumber(real, -imaginary, accuracy);
    }
    
    public String toString()
    {
        if(isReal())
        {
            return real + "";
        }

        if(isImaginary())
        {
            return imaginary + "i";
        }

        if(imaginary > accuracy && real > accuracy) // im > 0 re >< 0
        {
            return real + " + i" + imaginary;
        }

        return real + " - i" + -imaginary; // im < 0 re >< 0
    }

    public double getReal()
    {
        return real;
    }

    public double getImaginary()
    {
        return imaginary;
    }

    public boolean equals(ComplexNumber num)
    {
        return num.getReal() == real && num.getImaginary() == imaginary;
    }

    public boolean equals(double num)
    {
        return num == real && 0 == imaginary;
    }

    public  boolean isImaginary()
    {
        return (real < accuracy && real > -accuracy);
    }

    public boolean isReal()
    {
        return (imaginary < accuracy && imaginary > -accuracy);
    }

}
